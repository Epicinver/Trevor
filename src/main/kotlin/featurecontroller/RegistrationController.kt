package featurecontroller

import annotation.BotCommand
import bot.SmlSalaryBot


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
class RegistrationController(val trevor: SmlSalaryBot) : Controller {

    @BotCommand("/start")
    fun performStart(chatId: Long) {
        trevor.performSendMessage(chatId, "Hello. Again")
    }

    @BotCommand("/reg")
    fun performRegistration(chatId: Long) {
        trevor.performSendMessage(chatId, "You say login")
    }
}