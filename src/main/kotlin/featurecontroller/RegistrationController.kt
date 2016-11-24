package featurecontroller

import annotation.BotCommand
import bot.SmlSalaryBot
import database.DatabaseHelper
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import res.Stickers
import res.UserStrings
import service.RegistrationService
import utils.DateValidator
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
object RegistrationController : Controller {

    private val bot = Injekt.get<SmlSalaryBot>()
    private val service = Injekt.get<RegistrationService>()
    
    @BotCommand("/reg")
    fun performRegistration(message: Message) {
        if (service.isExist(message)) {
            bot.performSendMessage(message.chatId, UserStrings.alreadyRegistered)
            service.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_USERNAME,
                    message.from.userName)
            return
        }
        if (TextUtils.isEmpty(message.from.userName)) {
            bot.performSendMessage(message.chatId, UserStrings.thereIsNoUsername)
        } else {
            bot.performSendMessage(message.chatId, UserStrings.askPass)
        }
    }

    fun askPass(message: Message) {
        if (message.text != UserStrings.pass) {
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
            if (DateValidator.validateBirthday(message.text)) {
                service.updateUser(message.chatId,
                        DatabaseHelper.COLUMN_BIRTHDAY,
                        message.text,
                        true)
                bot.performSendMessage(message.chatId, UserStrings.registrationComplete)
                bot.performSendSticker(message.chatId, Stickers.registrationComplete)
            } else {
                bot.performSendMessage(message.chatId, UserStrings.incorrectBirthday)
            }
        } else {
            service.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_SML_NAME,
                    message.text)
            bot.performSendMessage(message.chatId, UserStrings.typeYourBirthday)
        }
    }

    fun isRegistered(message: Message): Boolean = service.isExist(message)
}


