package feature.reservation

import entity.Reservation
import feature.base.BaseService
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationService : BaseService() {

    val reservationRepository = Injekt.get<Repository<Reservation>>()
    val map = ConcurrentHashMap<Long, Reservation>()


    fun createReserve(message: Message, room: Rooms) {
        when (room) {
            Rooms.BIG -> map.put(message.chatId, Reservation(chatId = message.chatId, roomId = 1))
            Rooms.SMALL -> map.put(message.chatId, Reservation(chatId = message.chatId, roomId = 2))
        }
    }

    fun updateReserve(reservation: Reservation) {
        reservationRepository.update(reservation)
    }


    fun isReserveExist(message: Message): Boolean =
            reservationRepository.getById(message.chatId) != null


    fun isReserveCompleted(message: Message): Boolean =
            hasDate(message) && hasDuration(message)

    fun hasDate(message: Message): Boolean = with(reservationRepository.getById(message.chatId)) {
        this?.date != null
    }


    fun hasDuration(message: Message): Boolean = with(reservationRepository.getById(message.chatId)) {
        this?.duration != null

    }

    enum class Rooms {

        BIG, SMALL

    }

}