package feature.adminactions

import annotation.BotCallbackData
import annotation.BotCommand
import feature.base.BaseController
import org.telegram.telegrambots.api.objects.Message
import redis.clients.jedis.JedisPool
import res.*
import utils.InlineKeyboardFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import kotlin.properties.Delegates

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object AdminActionsController : BaseController<AdminActionsService>(AdminActionsService::class) {

    @BotCommand("/actions")
    fun performActionsShow(message: Message) {
        if (!service.isAdmin(message)) {
            bot.performSendMessage(message.chatId, AdminStrings.commandNotAllowed)
            return
        }
        service.storeMessage(bot.performSendMessage(message.chatId, AdminStrings.commandsList,
                InlineKeyboardFactory.createAdminKeyboard()))
    }


    @BotCallbackData(CallbackData.allUsers)
    fun showAllUsers(message: Message) {
        val list = StringBuilder()
        service.getAllUsers()
                .map { user -> "${user.smlName}     ${user.chatId} \n" }
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

    @BotCallbackData(CallbackData.deleteUser)
    fun deleteUser(message: Message) {
        bot.performSendMessage(message.chatId, AdminStrings.typeChatIdToDelete, forceReply = true)
    }

    @BotCallbackData(CallbackData.salaryToday)
    fun sendSalaryNotification(message: Message) {
        service.getAllUsers()
                .forEach {
                    bot.performSendMessage(it.chatId, RegistrationStrings.salaryNotification,
                            InlineKeyboardFactory.createUserNotificationKeyboard())
                }
        with(service.extractMessage()) {
            bot.performEditKeyboard(message.chatId, this.messageId,
                    InlineKeyboardFactory.createEditedAdminKeyboard())
        }

    }

    fun performDeleteUser(message: Message) {
        bot.performSendMessage(message.chatId, AdminStrings.userHasBeenDeleted)
        service.deleteUser(message.text.toLong())
    }
}