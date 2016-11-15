package featurecontroller

import annotation.BotCommand
import bot.Trevor
import constant.Strings
import database.DatabaseHelper
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import registration.RegistrationService
import utils.InlineKeyboardFactory


/**
 * Created by sergeyopivalov on 08/11/2016.
 */
object RegistrationController : Controller {
    //todo вызывать методы у интерфейса smlSalaryBot
    @BotCommand("/reg")
    fun performRegistration(message: Message) {
        if (RegistrationService.isRegistered(message)) {
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

    //todo hardcode should be moved out

    fun askPass(message: Message) {
            if (message.text == Strings.pass) {
                Trevor.performSendMessage(message.chatId, Strings.rightPass)
                RegistrationService.confirmRegistration(message)
                Trevor.performSendMessage(message.chatId, "Введи имя!")
            } else {
                Trevor.performSendMessage(message.chatId, Strings.wrongPass)
            }
    }

    fun updateUser (message: Message) {
        if (RegistrationService.isRegistrationCompleted(message)) {
            Trevor.performSendMessage(message.chatId, "Что?")
            return
        }
        if (RegistrationService.hasSmlName(message)) {
            RegistrationService.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_BIRTHDAY,
                    message.text,
                    true)
            Trevor.performSendMessage(message.chatId, "Регистрация завершена")
        } else {
            RegistrationService.updateUser(message.chatId,
                    DatabaseHelper.COLUMN_SML_NAME,
                    message.text)
            Trevor.performSendMessage(message.chatId, "А теперь дату рождения")
        }
    }

    fun isRegistered(message: Message) : Boolean = RegistrationService.isRegistered(message)

}

