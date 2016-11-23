package featurecontroller

import annotation.BotCallbackData
import bot.SmlSalaryBot
import entity.User
import task.SalaryTask
import org.telegram.telegrambots.api.objects.Message
import res.MiscStrings
import res.SalaryDayStrings
import service.SalaryService
import utils.InlineKeyboardFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by sergeyopivalov on 20.11.16.
 */
object SalaryController : Controller {

    private var adminMessage: Message by Delegates.notNull()
    private var currentMessage: Message by Delegates.notNull()
    private var currentUser: User? = null

    private val bot = Injekt.get<SmlSalaryBot>()
    private val service = Injekt.get<SalaryService>()
    private var timer = Injekt.get<Timer>()
    private var timerTask : TimerTask? = null

    @BotCallbackData("#salaryYes")
    fun addUserToSalaryList(message: Message) {
        service.addUserToSalaryList(message)
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.hasBeenAdded, true)
    }

    //todo название метода
    @BotCallbackData("#salaryNo")
    fun userNoSalary(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.inOtherTime, true)
    }

    //todo more kotlin in this method
    @BotCallbackData("#salaryList")
    fun showSalaryList(message: Message) {
        val list = with(service.getAllUsersForSalary()) {
            if (this.isEmpty()) {
                bot.performSendMessage(message.chatId, SalaryDayStrings.noOne)
                return
            }
            val list = StringBuilder()
            this.map { user -> "${user.smlName} \n" }
                    .forEach { list.append(it) }

            list.append("${SalaryDayStrings.quantity} ${this.size}")
            list.toString()
        }
        bot.performSendMessage(message.chatId, list) //todo Feature:запомнить сообщение и редактировать его, а не присылать новое
    }

    //todo название метода
    @BotCallbackData("#salaryReady")
    fun userReady(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, MiscStrings.ok, true)
        timerTask?.cancel()
    }

    @BotCallbackData("#salaryNotReady")
    fun skipTurn(message: Message) {
        bot.performSendMessage(message.chatId, SalaryDayStrings.turnSkipped)
        notifyNextUser()
    }

    @BotCallbackData("#salaryStart")
    fun startSalary(message: Message) {
        adminMessage = bot.performSendMessage(message.chatId, SalaryDayStrings.dummy)
        notifyNextUser()
    }

    @BotCallbackData("#userGetSalary")
    fun userGetSalary(message: Message) {
        bot.performEditMessage(currentMessage.chatId, currentMessage.messageId, SalaryDayStrings.moneyReceived, true)
        service.deleteUserFromSalaryList(currentUser!!)
        notifyNextUser()
    }

    @BotCallbackData("#userNotGetSalary")
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
            return
        }

        currentUser = service.getNextUser(currentUser)
        notifyAdmin()
        currentMessage = inviteUser()

        timerTask = SalaryTask(currentMessage)
        timer.schedule(timerTask, 5000) //todo delay to properties
    }

    private fun notifyAdmin() {
        with (bot) {
            performEditMessage(adminMessage.chatId, adminMessage.messageId,
                    "${currentUser?.smlName} ${SalaryDayStrings.isGoing}")
            performEditKeyboard(adminMessage.chatId, adminMessage.messageId,
                    InlineKeyboardFactory.createUserStatusKeyboard())
        }
    }

    private fun inviteUser() : Message =
            bot.performSendMessage(currentMessage.chatId, SalaryDayStrings.yourTurn,
                InlineKeyboardFactory.createUserReadyKeyboard())



}