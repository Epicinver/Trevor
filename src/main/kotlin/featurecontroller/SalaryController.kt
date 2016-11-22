package featurecontroller

import annotation.BotCallbackData
import bot.SmlSalaryBot
import task.SalaryTask
import org.telegram.telegrambots.api.objects.Message
import res.MiscStrings
import res.SalaryDayStrings
import service.SalaryService
import utils.InlineKeyboardFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
object SalaryController : Controller {

    private var adminMessage = Message()  //todo ???

    private val bot = Injekt.get<SmlSalaryBot>()
    private val service = Injekt.get<SalaryService>()
    private val timer = Injekt.get<Timer>()

    @BotCallbackData("#salaryYes")
    fun addUserToSalaryList(message: Message) {
        service.addUserToSalaryList(message)
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.hasBeenAdded, true)
    }

    //todo тупое название метода
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
        bot.performSendMessage(message.chatId, MiscStrings.ok)
        timer.cancel()

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

    fun notifyUserSkipTurn(message: Message) {
        bot.performEditMessage(message.chatId, message.messageId, SalaryDayStrings.turnSkipped)
    }

    fun notifyNextUser() {
        val message = with(service.getUserForSalary()) {
            bot.performEditMessage(adminMessage.chatId, adminMessage.messageId,
                    "${this.smlName} ${SalaryDayStrings.isGoing}")
            bot.performSendMessage(this.chatId, SalaryDayStrings.yourTurn,
                    InlineKeyboardFactory.createUserReadyKeyboard())
        }
        timer.schedule(SalaryTask(message), 5000)
    }


}