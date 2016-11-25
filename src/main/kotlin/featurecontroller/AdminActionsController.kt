package featurecontroller

import annotation.BotCallbackData
import annotation.BotCommand
import bot.SmlSalaryBot
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Sticker
import res.*
import service.AdminActionsService
import utils.InlineKeyboardFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import kotlin.properties.Delegates

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object AdminActionsController : BaseController() {

    //todo добавить возможность удалять юзера из БД
    private var messageWithActions: Message by Delegates.notNull()

    //todo перенести сервис в базовый класс (там гемор с дженериками)
    private val service = Injekt.get<AdminActionsService>()

    @BotCommand("/actions")
    fun performActionsShow(message: Message) {
        if (!service.isAdmin(message)) {
            bot.performSendMessage(message.chatId, AdminStrings.commandNotAllowed)
            return
        }
        messageWithActions = bot.performSendMessage(message.chatId, AdminStrings.commandsList,
                InlineKeyboardFactory.createAdminKeyboard())
    }


    @BotCallbackData(CallbackData.allNames)
    fun showAllNames(message: Message) {
        val list = StringBuilder()
        service.getAllUsers()
                .map { user -> "${user.smlName} \n" }
                .forEach { list.append(it) }

        bot.performSendMessage(message.chatId, list.toString())
    }

    @BotCallbackData(CallbackData.needHelp)
    fun helpRequest(message: Message) {
        with(service.getHelper().chatId) {
            bot.performSendMessage(this, AdminStrings.helpRequest)
            bot.performSendSticker(this, Stickers.helpRequest)
        }
        bot.performSendMessage(message.chatId, AdminStrings.helpGoing)

    }

    @BotCallbackData(CallbackData.salaryToday)
    fun sendSalaryNotification(message: Message) {
        service.getAllUsers()
                .forEach {
                    bot.performSendMessage(it.chatId, UserStrings.salaryNotification,
                            InlineKeyboardFactory.createUserNotificationKeyboard())
                }
        bot.performEditKeyboard(message.chatId, messageWithActions.messageId,
                InlineKeyboardFactory.createEditedAdminKeyboard())

    }
}