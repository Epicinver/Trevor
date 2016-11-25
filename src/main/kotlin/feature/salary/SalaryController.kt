package feature.salary

import annotation.BotCallbackData
import entity.User
import feature.base.BaseController
import feature.salary.task.SalaryTask
import org.telegram.telegrambots.api.objects.Message
import res.CallbackData
import res.MiscStrings
import res.SalaryDayStrings
import feature.salary.SalaryService
import utils.InlineKeyboardFactory
import utils.PropertiesLoader
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by sergeyopivalov on 20.11.16.
 */
object SalaryController : BaseController() {

    private val service = Injekt.get<SalaryService>()
    private var timer = Injekt.get<Timer>()

    private var adminMessage: Message by Delegates.notNull()
    private var currentMessage: Message by Delegates.notNull()
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

    //todo more kotlin need here
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

        if (salaryListMessage == null)
            salaryListMessage = bot.performSendMessage(message.chatId, list)
        else
            bot.performEditMessage(message.chatId, salaryListMessage!!.messageId, list)
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
        bot.performEditMessage(currentMessage.chatId, currentMessage.messageId, SalaryDayStrings.moneyReceived, true)
        service.deleteUserFromSalaryList(currentUser!!)
        notifyNextUser()
    }

    @BotCallbackData(CallbackData.notGotPaid)
    fun userNotGetSalary(message: Message) {
        notifyUserSkipTurn(currentMessage)
        notifyNextUser()
    }

    fun notifyUserSkipTurn(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.turnSkipped)
    }

    fun notifyNextUser() {
        timerTask?.cancel()

        if (service.isListEmpty()) {
            bot.performEditMessage(adminMessage.chatId, adminMessage.messageId, SalaryDayStrings.complete)
            salaryListMessage = null
            return
        }

        currentUser = service.getNextUser(currentUser)
        notifyAdmin()
        currentMessage = inviteUser()

        timerTask = SalaryTask(currentMessage)
        timer.schedule(timerTask, PropertiesLoader.getProperty("delay").toLong())
    }

    private fun notifyAdmin() {
        bot.apply {
            performEditMessage(feature.salary.SalaryController.adminMessage.chatId, feature.salary.SalaryController.adminMessage.messageId,
                    "${feature.salary.SalaryController.currentUser?.smlName} ${res.SalaryDayStrings.isGoing}")
            performEditKeyboard(feature.salary.SalaryController.adminMessage.chatId, feature.salary.SalaryController.adminMessage.messageId,
                    utils.InlineKeyboardFactory.createUserPaidStatusKeyboard())
        }
    }

    private fun inviteUser(): Message =
            bot.performSendMessage(currentMessage.chatId, SalaryDayStrings.yourTurn,
                    InlineKeyboardFactory.createUserInvitationKeyboard())


}