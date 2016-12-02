package feature.registration

import annotation.BotCommand
import feature.base.BaseController
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import res.MiscStrings
import res.Stickers
import res.UserStrings
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
            bot.performSendMessage(message.chatId, UserStrings.alreadyRegistered)
            service.updateUser(message.chatId, "username", message.from.userName)
            return
        }
        if (TextUtils.isEmpty(message.from.userName)) {
            bot.performSendMessage(message.chatId, UserStrings.thereIsNoUsername)
        } else {
            bot.performSendMessage(message.chatId, UserStrings.askPass)
        }
    }

    @BotCommand("/help")
    fun showHelp(message: Message) {
        bot.performSendMessage(message.chatId, MiscStrings.instruction)
    }

    fun askPass(message: Message) {
        if (message.text != PropertiesLoader.getProperty("pass")) {
            bot.performSendMessage(message.chatId, UserStrings.wrongPass)
            return
        }
        service.createUser(message)
        bot.performSendMessage(message.chatId, UserStrings.rightPass)
        bot.performSendMessage(message.chatId, UserStrings.typeYourName)
    }

    fun updateUser(message: Message) {
        if (service.isRegistrationCompleted(message)) {
            bot.performSendMessage(message.chatId, UserStrings.incorrectInput)
            return
        }
        if (service.hasSmlName(message)) {
            if (RegexValidator.validateBirthday(message.text)) {
                service.updateUser(message.chatId, "birthday", message.text)
                bot.performSendMessage(message.chatId, UserStrings.registrationComplete)
                bot.performSendSticker(message.chatId, Stickers.registrationComplete)
            } else {
                bot.performSendMessage(message.chatId, UserStrings.incorrectBirthday)
            }
        } else {
            service.updateUser(message.chatId, "smlName", message.text)
            bot.performSendMessage(message.chatId, UserStrings.typeYourBirthday)
        }
    }

    fun isRegistered(message: Message): Boolean = service.isUserExist(message)
}


