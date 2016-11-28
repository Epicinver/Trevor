package feature.reservation

import entity.Reservation
import feature.base.BaseService
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationService : BaseService() {

    val reservationRepository = Injekt.get<Repository<Reservation>>()

    fun reserve(message: Message, room: Rooms) {
        when (room) {
            Rooms.BIG -> reservationRepository.create(Reservation(message.chatId, 1))
            Rooms.SMALL -> reservationRepository.create(Reservation(message.chatId, 2))
        }
    }

    fun updateReserve(chatId: Long, key: String, value: String, closeDb: Boolean = false) {
        reservationRepository.update(chatId, key, value, closeDb)
    }


    fun isReserveExist(message: Message): Boolean =
            reservationRepository.getById(message.chatId) != null


    fun isReserveCompleted(message: Message): Boolean =
            hasTime(message) && hasDuration(message)

    fun hasTime(message: Message): Boolean {
        return with(reservationRepository.getById(message.chatId)) {
            !TextUtils.isEmpty(this?.time)
        }
    }


    fun hasDuration(message: Message): Boolean {
        return with(reservationRepository.getById(message.chatId)) {
            !TextUtils.isEmpty(this?.duration)
        }
    }

    enum class Rooms {

        BIG, SMALL

    }

}