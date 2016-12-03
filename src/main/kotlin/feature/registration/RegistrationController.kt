package feature.registration

import annotation.BotCommand
import feature.base.BaseController
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import res.MiscStrings
import res.Stickers
import res.RegistrationStrings
import utils.PropertiesLoader
import utils.RegexValidator


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
object RegistrationController : BaseController<RegistrationService>(RegistrationService::class) {

    @BotCommand("/start")
    fun showGreeting(message: Message) {
        bot.performSendMessage(message.chatId, MiscStrings.greeting)

        if (service.isUserExist(message)) {
            bot.performSendMessage(message.chatId, RegistrationStrings.alreadyRegistered)
            service.updateUser(message.chatId, "username", message.from.userName)
            return
        }
        if (TextUtils.isEmpty(message.from.userName)) {
            bot.performSendMessage(message.chatId, RegistrationStrings.thereIsNoUsername)
        } else {
            bot.performSendMessage(message.chatId, RegistrationStrings.askPass)
        }
    }

    @BotCommand("/help")
    fun showHelp(message: Message) {
        bot.performSendMessage(message.chatId, MiscStrings.instruction)
    }

    fun askPass(message: Message) {
        if (message.text != PropertiesLoader.getProperty("pass")) {
            bot.performSendMessage(message.chatId, RegistrationStrings.wrongPass)
            return
        }
        service.createUser(message)
        bot.performSendMessage(message.chatId, RegistrationStrings.rightPass)
        bot.performSendMessage(message.chatId, RegistrationStrings.typeYourName)
    }

    fun updateUser(message: Message) {
        if (service.isRegistrationCompleted(message)) {
            bot.performSendMessage(message.chatId, MiscStrings.incorrectInput)
            return
        }
        if (service.hasSmlName(message)) {
            updateBirthday(message)
        } else {
            updateSmlName(message)
        }
    }

    fun isRegistered(message: Message): Boolean = service.isUserExist(message)

    private fun updateSmlName(message: Message) {
        service.updateUser(message.chatId, "smlName", message.text)
        bot.performSendMessage(message.chatId, RegistrationStrings.typeYourBirthday)
    }

    private fun updateBirthday(message: Message) {
        if (RegexValidator.validateBirthday(message.text)) {
            service.updateUser(message.chatId, "birthday", message.text)
            bot.performSendMessage(message.chatId, RegistrationStrings.registrationComplete)
            bot.performSendSticker(message.chatId, Stickers.registrationComplete)
        } else {
            bot.performSendMessage(message.chatId, RegistrationStrings.incorrectBirthday)
        }
    }
}


