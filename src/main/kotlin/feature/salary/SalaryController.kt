package feature.salary

import annotation.BotCallbackData
import entity.User
import feature.base.BaseController
import feature.salary.task.SalaryTask
import org.telegram.telegrambots.api.objects.Message
import res.CallbackData
import res.MiscStrings
import res.SalaryDayStrings
import utils.InlineKeyboardFactory
import utils.PropertiesLoader
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by sergeyopivalov on 20.11.16.
 */
object SalaryController : BaseController<SalaryService>(SalaryService::class) {

    private var timer = Injekt.get<Timer>()

    private var adminMessage: Message? = null
    private var currentMessage: Message? = null
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
            this.map { user -> "${user.smlName} \n" }
                .forEach { list.append(it) }

            list.append("${SalaryDayStrings.quantity} ${this.size}")
            list.toString()
        }

        when (salaryListMessage) {
            null ->  salaryListMessage = bot.performSendMessage(message.chatId, list)
            else ->  bot.performEditMessage(message.chatId, salaryListMessage!!.messageId, list)
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
        notifyNextUser()
    }

    @BotCallbackData(CallbackData.gotPaid)
    fun userGetSalary(message: Message) {
        bot.performEditMessage(currentMessage!!.chatId, currentMessage!!.messageId, SalaryDayStrings.moneyReceived, true)
        service.deleteUserFromSalaryList(currentUser!!)
        notifyNextUser()
    }

    @BotCallbackData(CallbackData.notGotPaid)
    fun userNotGetSalary(message: Message) {
        notifyUserSkipTurn(currentMessage!!)
        notifyNextUser()
    }

    fun notifyUserSkipTurn(message: Message) =
            bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.turnSkipped)


    fun notifyNextUser() {
        timerTask?.cancel()

        if (service.isListEmpty()) {
            bot.performEditMessage(adminMessage!!.chatId, adminMessage!!.messageId, SalaryDayStrings.complete)
            salaryListMessage = null
            return
        }

        currentUser = service.getNextUser(currentUser)
        notifyAdmin()
        inviteUser()

        timerTask = SalaryTask(currentMessage!!)
        timer.schedule(timerTask, PropertiesLoader.getProperty("delay").toLong())
    }

    private fun notifyAdmin() {
        bot.apply {
            performEditMessage(adminMessage!!.chatId, adminMessage!!.messageId,
                    "${currentUser?.smlName} ${SalaryDayStrings.isGoing}")
            performEditKeyboard(SalaryController.adminMessage!!.chatId, adminMessage!!.messageId,
                    InlineKeyboardFactory.createUserPaidStatusKeyboard())
        }
    }

    private fun inviteUser() {
        currentMessage = bot.performSendMessage(currentUser!!.chatId, SalaryDayStrings.yourTurn,
                InlineKeyboardFactory.createUserInvitationKeyboard())
    }

}