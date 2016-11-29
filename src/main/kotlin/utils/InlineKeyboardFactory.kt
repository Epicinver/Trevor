package utils

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton
import res.ButtonLabel
import res.CallbackData
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

    fun createAdminKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.allUsers, CallbackData.allUsers)),
                    listOf(createButton(ButtonLabel.needHelp, CallbackData.needHelp)),
                    listOf(createButton(ButtonLabel.deleteUser, CallbackData.deleteUser)),
                    listOf(createButton(ButtonLabel.salaryToday, CallbackData.salaryToday)))
        }
    }

    fun createEditedAdminKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.allUsers, CallbackData.allUsers)),
                    listOf(createButton(ButtonLabel.needHelp, CallbackData.needHelp)),
                    listOf(createButton(ButtonLabel.deleteUser, CallbackData.deleteUser)),
                    listOf(createButton(ButtonLabel.salaryList, CallbackData.salaryList)),
                    listOf(createButton(ButtonLabel.salaryStart, CallbackData.salaryStart)))
        }
    }

    fun createUserNotificationKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(listOf(createButton(ButtonLabel.yes, CallbackData.addToSalaryList),
                    createButton(ButtonLabel.no, CallbackData.notAddToSalaryList)))
        }
    }

    fun createUserInvitationKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.going, CallbackData.goingToGetPaid),
                            createButton(ButtonLabel.notGoing, CallbackData.notGoingToGetPaid))
            )
        }
    }

    fun createUserPaidStatusKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.gotPaid, CallbackData.gotPaid),
                            createButton(ButtonLabel.notGotPaid, CallbackData.notGotPaid))
            )
        }
    }

    fun createReservationKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(
                    listOf(createButton(ButtonLabel.bigRoom, CallbackData.bigRoomReserve),
                            createButton(ButtonLabel.smallRoom, CallbackData.smallRoomReserve)),
                    listOf(createButton(ButtonLabel.reservesList, CallbackData.reservesList))
            )
        }
    }

}
