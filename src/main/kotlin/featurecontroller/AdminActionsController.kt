package featurecontroller

import annotation.BotCallbackData
import annotation.BotCommand
import bot.SmlSalaryBot
import constant.Strings
import org.telegram.telegrambots.api.objects.Message
import service.AdminActionsService
import utils.InlineKeyboardFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object AdminActionsController : Controller {

    val bot = Injekt.get<SmlSalaryBot>()
    val service = Injekt.get<AdminActionsService>()

    @BotCommand("/actions")
    fun performActionsShow(message: Message) {
        if (!service.isAdmin(message)) {
            bot.performSendMessage(message.chatId, Strings.commandNotAllowed)
            return
        }
        bot.performSendMessage(message.chatId, "Commands:", InlineKeyboardFactory.createAdminKeyboard())
    }

    @BotCallbackData("#allNames")
    fun showAllUsers(message: Message) {
        var list = ""
        for (name in service.getAllNames()) {
            list = list + name + "\n"
        }
        bot.performSendMessage(message.chatId, list)
    }
}