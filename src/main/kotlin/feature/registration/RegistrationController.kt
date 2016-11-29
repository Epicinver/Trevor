package feature.registration

import annotation.BotCommand
import database.DatabaseHelper
import feature.base.BaseController
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import entity.User
import res.Stickers
import res.UserStrings
import utils.PropertiesLoader
import utils.Validator
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
object RegistrationController : BaseController() {

    private val service = Injekt.get<RegistrationService>()

    @BotCommand("/reg")
    fun performRegistration(message: Message) {
        if (service.isExist(message)) {
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

    fun askPass(message: Message) {
        if (message.text != PropertiesLoader.getProperty("pass")) {
            bot.performSendMessage(message.chatId, UserStrings.wrongPass)
            return
        }
        bot.performSendMessage(message.chatId, UserStrings.rightPass)

        service.createUser(message)
        bot.performSendMessage(message.chatId, UserStrings.typeYourName)
    }

    fun updateUser(message: Message) {
        if (service.isRegistrationCompleted(message)) {
            bot.performSendMessage(message.chatId, UserStrings.incorrectInput)
            return
        }
        if (service.hasSmlName(message)) {
            if (Validator.validateBirthday(message.text)) {
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

    fun isRegistered(message: Message): Boolean = service.isExist(message)
}


