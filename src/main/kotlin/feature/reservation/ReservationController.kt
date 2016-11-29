package feature.reservation

import annotation.BotCallbackData
import annotation.BotCommand
import entity.Reservation
import feature.base.BaseController
import org.telegram.telegrambots.api.objects.Message
import res.CallbackData
import res.ReservationStrings
import utils.InlineKeyboardFactory
import utils.Validator
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
        service.createReserve(message, ReservationService.Rooms.BIG)
        bot.performSendMessage(message.chatId, ReservationStrings.typeDate)
    }

    @BotCallbackData(CallbackData.smallRoomReserve)
    fun smallRoomReserve(message: Message) {
        service.createReserve(message, ReservationService.Rooms.SMALL)
        bot.performSendMessage(message.chatId, ReservationStrings.typeDate)
    }

    fun updateReservation(message: Message) {
        if (service.isReserveCompleted(message)) return
        if (service.hasDate(message)) {
            if (Validator.validateReserveDuration(message.text)) {
                service.updateDuration(message)
                bot.performSendMessage(message.chatId, ReservationStrings.reserved)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectDuration)
            }
        } else {
            if (Validator.validateReserveDate(message.text)) {
                service.updateDate(message)
                bot.performSendMessage(message.chatId, ReservationStrings.typeDuration)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectDate)
            }
        }
    }

    fun isReserveCreated(message: Message): Boolean =
            service.isReserveExist(message) && !service.isReserveCompleted(message)

}