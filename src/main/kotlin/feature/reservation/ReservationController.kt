package feature.reservation

import annotation.BotCallbackData
import annotation.BotCommand
import database.DatabaseHelper
import feature.base.BaseController
import org.telegram.telegrambots.api.objects.Message
import res.CallbackData
import res.ReservationStrings
import utils.InlineKeyboardFactory
import utils.RegexValidator
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 26.11.16.
 */
object ReservationController : BaseController() {

    val service = Injekt.get<ReservationService>()

    @BotCommand("/reserve")
    fun performChooseRoom(message: Message) {
        bot.performSendMessage(message.chatId, ReservationStrings.chooseRoom,
                InlineKeyboardFactory.createChoosingRoomKeyboard())

    }

    @BotCallbackData(CallbackData.bigRoomReserve)
    fun bigRoomReserve(message: Message) {
        service.reserve(message, ReservationService.Rooms.BIG)
        bot.performSendMessage(message.chatId, ReservationStrings.typeTime)
    }

    @BotCallbackData(CallbackData.smallRoomReserve)
    fun smallRoomReserve(message: Message) {
        service.reserve(message, ReservationService.Rooms.SMALL)
        bot.performSendMessage(message.chatId, ReservationStrings.typeTime)
    }

    fun updateReservation(message: Message) {
        if (service.isReserveCompleted(message)) return
        if (service.hasTime(message)) {
            if (RegexValidator.validateReserveDuration(message.text)) {
                service.updateReserve(message.chatId,
                        DatabaseHelper.COLUMN_DURATION,
                        message.text,true)
                bot.performSendMessage(message.chatId, ReservationStrings.reserved)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectDuration)
            }
        } else {
            if (RegexValidator.validateReserveTime(message.text)) {
                service.updateReserve(message.chatId,
                        DatabaseHelper.COLUMN_TIME,
                        message.text)
                bot.performSendMessage(message.chatId, ReservationStrings.typeDuration)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectTime)
            }
        }
    }

    fun isReserveCreated(message: Message): Boolean =
            service.isReserveExist(message)&& !service.isReserveCompleted(message)

}