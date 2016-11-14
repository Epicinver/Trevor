package featurecontroller

import annotation.BotCommand
import bot.Trevor
import constant.Strings
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
        if (RegistrationService.alreadyRegistered(message)) {
            Trevor.performSendMessage(message.chatId, Strings.alreadyRegistered)
            RegistrationService.updateUsername(message.chatId, message.from.userName)
            return
        }
        if (TextUtils.isEmpty(message.from.userName)) {
            Trevor.performSendMessage(message.chatId, Strings.thereIsNoUsername)
        } else {
            RegistrationService.acceptRegistration(message)
            Trevor.performSendMessage(message.chatId, Strings.askPass)
        }
    }

    fun checkRegistration(message: Message) {
        if (RegistrationService.alreadyRegistered(message) &&
                !RegistrationService.hasSmlName(message)) {
            if (message.text == Strings.pass) {
                Trevor.performSendMessage(message.chatId, Strings.rightPass,
                        InlineKeyboardFactory.registrationKeyboard())
            } else {
                Trevor.performSendMessage(message.chatId, Strings.wrongPass)
            }
        }
    }


}

