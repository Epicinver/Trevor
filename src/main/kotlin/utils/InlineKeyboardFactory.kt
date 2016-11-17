package utils

import constant.Strings
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton

/**
 * Created by sergeyopivalov on 11/11/2016.
 */
object InlineKeyboardFactory {

    private fun createButton(text: String, callbackData: String): InlineKeyboardButton {
        return InlineKeyboardButton().apply {
            this.text = text
            this.callbackData = callbackData
        }

    }

    //todo hardcode move out
    fun createAdminKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(Strings.buttonAllUsers, "#allNames")),
                    listOf(createButton(Strings.buttonNeedHelp, "#needHelp")),
                    listOf(createButton(Strings.buttonSalaryToday, "#salaryToday")))
        }
    }

    fun createEditedAdminKeyboard() : InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(Strings.buttonAllUsers, "#allNames")),
                    listOf(createButton(Strings.buttonNeedHelp, "#needHelp")),
                    listOf(createButton(Strings.buttonSalaryList, "#salaryList")),
                    listOf(createButton(Strings.buttonSalaryStart, "#salaryStart")))
        }
    }

    fun createUserNotificationKeyboard() : InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(listOf(createButton(Strings.buttonYes, "#salaryYes"),
                    createButton(Strings.buttonNo, "#salaryNo")))
        }
    }

}
