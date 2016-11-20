package featurecontroller

import annotation.BotCallbackData
import annotation.BotCommand
import bot.SmlSalaryBot
import org.telegram.telegrambots.api.objects.Message
import res.AdminStrings
import res.UserStrings
import service.AdminActionsService
import utils.InlineKeyboardFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object AdminActionsController : Controller {

    var messageWithActions = -1

    val bot = Injekt.get<SmlSalaryBot>()
    val service = Injekt.get<AdminActionsService>()


    //todo hardcode!
    @BotCommand("/actions")
    fun performActionsShow(message: Message) {
        if (!service.isAdmin(message)) {
            bot.performSendMessage(message.chatId, AdminStrings.commandNotAllowed)
            return
        }
        messageWithActions = bot.performSendMessage(message.chatId, "Commands:",
                InlineKeyboardFactory.createAdminKeyboard()).messageId
    }

    @BotCallbackData("#allNames")
    fun showAllNames(message: Message) {
        val allNames = StringBuilder()
        service.getAllUsers()
                .map { user -> "${user.smlName} \n" }
                .forEach { allNames.append(it) }

        bot.performSendMessage(message.chatId, allNames.toString())
    }

    //todo sticker factory!!!
    @BotCallbackData("#needHelp")
    fun helpRequest(message: Message) {
        with(service.getHelper().chatId) {
            bot.performSendMessage(this, AdminStrings.helpRequest)
            bot.performSendSticker(this, "BQADAQADch8AAtpxZgcZflwMawhtDQI")
        }
        bot.performSendMessage(message.chatId, AdminStrings.helpGoing)

    }

    @BotCallbackData("#salaryToday")
    fun sendSalaryNotification(message: Message) {
        service.getAllUsers()
                .forEach {
                    bot.performSendMessage(it.chatId, UserStrings.salaryNotification,
                            InlineKeyboardFactory.createUserNotificationKeyboard())
                }
        bot.performEditKeyboard(message.chatId, messageWithActions,
                InlineKeyboardFactory.createEditedAdminKeyboard())

    }
}