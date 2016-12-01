package feature.reservation

import annotation.BotCallbackData
import annotation.BotCommand
import feature.base.BaseController
import org.telegram.telegrambots.api.objects.Message
import res.CallbackData
import res.ReservationStrings
import utils.InlineKeyboardFactory
import utils.RegexValidator
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.text.SimpleDateFormat

/**
 * Created by sergeyopivalov on 26.11.16.
 */
object ReservationController : BaseController() {

    val dateFormat = "dd.MM.yyyy HH:mm"
    val service = Injekt.get<ReservationService>()

    @BotCommand("/reserve")
    fun performChooseRoom(message: Message) {
        bot.performSendMessage(message.chatId, ReservationStrings.chooseRoom,
                InlineKeyboardFactory.createReservationKeyboard())

    }

    @BotCallbackData(CallbackData.reservesList)
    fun showReservesList(message: Message) {
        val list = StringBuilder()

        with(service.getAllReserves()) {
            if (this.isEmpty()) {
                bot.performSendMessage(message.chatId, ReservationStrings.noReserves)
                return
            }
            this.forEach {
                list.append("${it.room!!.description} " +
                        "   ${SimpleDateFormat(dateFormat).format(it.start!!)}" +
                        "   ${it.user!!.smlName}" +
                        "   ${(it.end!! - it.start!!) / 1000 / 60}min\n")
            }
            bot.performSendMessage(message.chatId, list.toString())

        }
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
        if (service.hasStart(message)) {
            if (RegexValidator.validateReserveDuration(message.text)) {
                service.updateEnd(message)
                bot.performSendMessage(message.chatId, ReservationStrings.reserved)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectDuration)
            }
        } else {
            if (RegexValidator.validateReserveDate(message.text)) {
                if (!service.isTimeAvailable(message)) {
                    bot.performSendMessage(message.chatId, ReservationStrings.timeNotAvailable)
                    return
                }
                service.updateStart(message)
                bot.performSendMessage(message.chatId, ReservationStrings.typeDuration)
            } else {
                bot.performSendMessage(message.chatId, ReservationStrings.incorrectDate)
            }
        }
    }

    fun isReserveCreated(message: Message): Boolean =
            service.isReserveExist(message) && !service.isReserveCompleted(message)

    fun cleanReservation(id: Int) {
        service.deleteReserve(id)
    }

}
