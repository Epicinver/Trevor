package featurecontroller

import annotation.BotCallbackData
import bot.SmlSalaryBot
import task.SalaryTask
import org.knowm.sundial.SundialJobScheduler
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
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

    private var adminMessageId = -1

    private val bot = Injekt.get<SmlSalaryBot>()
    private val service = Injekt.get<SalaryService>()

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

    @BotCallbackData("#salaryStart")
    fun startSalary(message: Message) {
        adminMessageId = bot.performSendMessage(message.chatId, SalaryDayStrings.dummy).messageId
        notifyNextUser(message)
    }

    fun notifyUserSkipTurn(message: Message) {
        bot.performSendMessage(message.chatId, SalaryDayStrings.turnSkipped)

    }

    private fun notifyNextUser(message: Message) {
        val userMessage = with(service.getUserForSalary()) {
            bot.performEditMessage(message.chatId, adminMessageId, "${this.smlName} ${SalaryDayStrings.isGoing}")
            bot.performSendMessage(this.chatId, SalaryDayStrings.yourTurn,
                    InlineKeyboardFactory.createUserReadyKeyboard())
        }

        Timer().schedule(SalaryTask(userMessage), 5000)
    }


}