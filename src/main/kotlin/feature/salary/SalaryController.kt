package feature.salary

import annotation.BotCallbackData
import entity.User
import feature.base.BaseController
import feature.salary.task.SalaryTask
import org.telegram.telegrambots.api.objects.Message
import res.*
import utils.InlineKeyboardFactory
import utils.PropertiesLoader
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
object SalaryController : BaseController<SalaryService>(SalaryService::class) {

    private var timer = Injekt.get<Timer>()

    private var adminMessage: Message? = null
    private var salaryListMessage: Message? = null
    private var currentUser: User? = null
    private var timerTask: TimerTask? = null

    @BotCallbackData(CallbackData.addToSalaryList)
    fun addUserToSalaryList(message: Message) {
        service.addUserToSalaryList(message)
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.hasBeenAdded, true)
    }

    @BotCallbackData(CallbackData.notAddToSalaryList)
    fun notAddUserToSalaryList(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.inOtherTime, true)
    }

    @BotCallbackData(CallbackData.salaryList)
    fun showSalaryList(message: Message) {
        val list = with(service.getAllUsersForSalary()) {
            if (isEmpty()) {
                bot.performSendMessage(message.chatId, SalaryDayStrings.noOne)
                return
            }
            val list = StringBuilder()
            this.map { "${it.smlName} \n" }
                    .forEach { list.append(it) }

            list.append("${SalaryDayStrings.quantity} ${this.size}")
            list.toString()
        }

        when (salaryListMessage) {
            null -> salaryListMessage = bot.performSendMessage(message.chatId, list)
            else -> bot.performEditMessage(message.chatId, salaryListMessage!!.messageId, list)
        }

    }

    @BotCallbackData(CallbackData.goingToGetPaid)
    fun userGoingToGetPaid(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, MiscStrings.ok, true)
        timerTask?.cancel()
    }

    @BotCallbackData(CallbackData.notGoingToGetPaid)
    fun skipTurn(message: Message) {
        bot.performSendMessage(message.chatId, SalaryDayStrings.turnSkipped)
        notifyNextUser()
    }

    @BotCallbackData(CallbackData.salaryStart)
    fun startSalary(message: Message) {
        adminMessage = bot.performSendMessage(message.chatId, SalaryDayStrings.dummy)
        service.storeInRedis(Key.adminMessage, adminMessage!!)
        notifyNextUser()
    }

    @BotCallbackData(CallbackData.gotPaid)
    fun userGotSalary(message: Message) {
        service.extractFromRedis(Key.currentMessage, Message::class.java).apply {
            bot.performEditMessage(this!!.chatId, this.messageId, SalaryDayStrings.moneyReceived, true)
        }
        service.extractFromRedis(Key.currentUser, User::class.java).apply {
            service.deleteUserFromSalaryList(this!!)
        }
        notifyNextUser()

    }

    @BotCallbackData(CallbackData.notGotPaid)
    fun userNotGotSalary(message: Message) {
        service.extractFromRedis(Key.currentMessage, Message::class.java).apply {
            notifyUserSkipTurn(this!!)
        }
        notifyNextUser()

    }

    fun notifyUserSkipTurn(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.turnSkipped)
        bot.performSendSticker(message.chatId, Stickers.slowpoke)
    }


    fun notifyNextUser() {
        timerTask?.cancel()

        adminMessage = service.extractFromRedis(Key.adminMessage, Message::class.java)
        if (service.isListEmpty()) {
            salaryComplete()
            return
        }

        currentUser = service.getNextUser(currentUser)
        service.storeInRedis(Key.currentUser, currentUser!!)

        notifyAdmin()
        inviteUser()
        startTimer()
    }

    private fun salaryComplete() {
        bot.performEditMessage(adminMessage!!.chatId, adminMessage!!.messageId, SalaryDayStrings.complete)
        service.salaryComplete()
        salaryListMessage = null
    }

    private fun startTimer() {
        with(service.extractFromRedis(Key.currentMessage, Message::class.java)) {
            timerTask = SalaryTask(this!!)
            timer.schedule(timerTask, PropertiesLoader.getProperty("delay").toLong())
        }
    }

    private fun notifyAdmin() {
        adminMessage = service.extractFromRedis(Key.adminMessage, Message::class.java)
        currentUser = service.extractFromRedis(Key.currentUser, User::class.java)
        bot.apply {
            performEditMessage(adminMessage!!.chatId, adminMessage!!.messageId,
                    "${currentUser?.smlName} ${SalaryDayStrings.isGoing}")
            performEditKeyboard(adminMessage!!.chatId, adminMessage!!.messageId,
                    InlineKeyboardFactory.createUserPaidStatusKeyboard())
        }
    }

    private fun inviteUser() {
        currentUser = service.extractFromRedis(Key.currentUser, User::class.java)
        service.storeInRedis(Key.currentMessage,
                bot.performSendMessage(currentUser!!.chatId, SalaryDayStrings.yourTurn,
                        InlineKeyboardFactory.createUserInvitationKeyboard()))
    }

}