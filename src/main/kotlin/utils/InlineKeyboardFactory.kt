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

    fun registrationKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(Strings.stepName, "name")),
                    listOf(createButton(Strings.stepBirthday, "birthday"))
            )

    }
}
}
