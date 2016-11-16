package featurecontroller

import annotation.BotCommand
import bot.SmlSalaryBot
import constant.Strings
import database.DatabaseHelper
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import registration.RegistrationService
import utils.DateValidator
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
object RegistrationController : Controller {

    val bot = Injekt.get<SmlSalaryBot>()
    val service = Injekt.get<RegistrationService>()
    
    @BotCommand("/reg")
    fun performRegistration(message: Message) {
        if (service.isExist(message)) {
            bot.performSendMessage(message.chatId, Strings.alreadyRegistered)
            service.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_USERNAME,
                    message.from.userName)
            return
        }
        if (TextUtils.isEmpty(message.from.userName)) {
            bot.performSendMessage(message.chatId, Strings.thereIsNoUsername)
        } else {
            bot.performSendMessage(message.chatId, Strings.askPass)
        }
    }

    fun askPass(message: Message) {
        if (message.text != Strings.pass) {
            bot.performSendMessage(message.chatId, Strings.wrongPass)
            return
        }
        bot.performSendMessage(message.chatId, Strings.rightPass)
        service.createUser(message)
        bot.performSendMessage(message.chatId, Strings.typeYourName)
    }

    fun updateUser(message: Message) {
        if (service.isRegistrationCompleted(message)) {
            bot.performSendMessage(message.chatId, Strings.incorrectInput)
            return
        }
        if (service.hasSmlName(message)) {
            if (DateValidator.validateBirthday(message.text)) {
                service.updateUser(message.chatId,
                        DatabaseHelper.COLUMN_BIRTHDAY,
                        message.text,
                        true)
                bot.performSendMessage(message.chatId, Strings.registrationComplete)
            } else {
                bot.performSendMessage(message.chatId, Strings.incorrectBirthday)
            }
        } else {
            service.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_SML_NAME,
                    message.text)
            bot.performSendMessage(message.chatId, Strings.typeYourBirthday)
        }
    }

    fun isRegistered(message: Message): Boolean = service.isExist(message)
}


