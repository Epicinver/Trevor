package featurecontroller

import annotation.BotCommand
import bot.Trevor
import constant.Strings
import database.DatabaseHelper
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import registration.RegistrationService
import utils.DateValidator
import utils.InlineKeyboardFactory


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
object RegistrationController : Controller {
    //todo вызывать методы у интерфейса smlSalaryBot
    @BotCommand("/reg")
    fun performRegistration(message: Message) {
        if (RegistrationService.isExist(message)) {
            Trevor.performSendMessage(message.chatId, Strings.alreadyRegistered)
            RegistrationService.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_USERNAME,
                    message.from.userName)
            return
        }
        if (TextUtils.isEmpty(message.from.userName)) {
            Trevor.performSendMessage(message.chatId, Strings.thereIsNoUsername)
        } else {
            Trevor.performSendMessage(message.chatId, Strings.askPass)
        }
    }

    fun askPass(message: Message) {
        if (message.text != Strings.pass) {
            Trevor.performSendMessage(message.chatId, Strings.wrongPass)
            return
        }
        Trevor.performSendMessage(message.chatId, Strings.rightPass)
        RegistrationService.createUser(message)
        Trevor.performSendMessage(message.chatId, Strings.typeYourName)
    }

    fun updateUser(message: Message) {
        if (RegistrationService.isRegistrationCompleted(message)) {
            Trevor.performSendMessage(message.chatId, Strings.incorrectInput)
            return
        }
        if (RegistrationService.hasSmlName(message)) {
            if (DateValidator.validateBirthday(message.text)) {
                RegistrationService.updateUser(message.chatId,
                        DatabaseHelper.COLUMN_BIRTHDAY,
                        message.text,
                        true)
                Trevor.performSendMessage(message.chatId, Strings.registrationComplete)
            } else {
                Trevor.performSendMessage(message.chatId, Strings.incorrectBirthday)
            }
        } else {
            RegistrationService.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_SML_NAME,
                    message.text)
            Trevor.performSendMessage(message.chatId, Strings.typeYourBirthday)
        }
    }

    fun isRegistered(message: Message): Boolean = RegistrationService.isExist(message)
}


