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
        bot.performSendMessage(message.chatId, ReservationStrings.typeTime)
    }

    @BotCallbackData(CallbackData.smallRoomReserve)
    fun smallRoomReserve(message: Message) {
        service.createReserve(message, ReservationService.Rooms.SMALL)
        bot.performSendMessage(message.chatId, ReservationStrings.typeTime)
    }

    fun updateReservation(message: Message) {
        if (service.isReserveCompleted(message)) return
        if (service.hasDate(message)) {
            if (Validator.validateReserveDuration(message.text)) {
                service.updateReserve(Reservation(chatId = message.chatId, duration = message.text.toLong())) //TODO!!!!!
                bot.performSendMessage(message.chatId, ReservationStrings.reserved)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectDuration)
            }
        } else {
            if (Validator.validateReserveTime(message.text)) {
                service.updateReserve(message.chatId, )
                bot.performSendMessage(message.chatId, ReservationStrings.typeDuration)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectTime)
            }
        }
    }


    fun isReserveCreated(message: Message): Boolean =
            service.isReserveExist(message) && !service.isReserveCompleted(message)

}