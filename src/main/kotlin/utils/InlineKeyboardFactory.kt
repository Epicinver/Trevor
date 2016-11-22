package utils

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton
import res.ButtonLabel
import java.io.BufferedReader

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
                    listOf(createButton(ButtonLabel.allNames, "#allNames")),
                    listOf(createButton(ButtonLabel.needHelp, "#needHelp")),
                    listOf(createButton(ButtonLabel.salaryToday, "#salaryToday")))
        }
    }

    fun createEditedAdminKeyboard() : InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.allNames, "#allNames")),
                    listOf(createButton(ButtonLabel.needHelp, "#needHelp")),
                    listOf(createButton(ButtonLabel.salaryList, "#salaryList")),
                    listOf(createButton(ButtonLabel.salaryStart, "#salaryStart")))
        }
    }

    fun createUserNotificationKeyboard() : InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(listOf(createButton(ButtonLabel.salaryYes, "#salaryYes"),
                    createButton(ButtonLabel.salaryNo, "#salaryNo")))
        }
    }

    //todo название !!!!
    fun createUserReadyKeyboard() : InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.ready, "#salaryReady"),
                            createButton(ButtonLabel.notReady, "#salaryNotReady"))
            )
        }
    }

}
