package feature.reservation

import entity.Reservation
import feature.base.BaseService
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.sql.Timestamp
import java.text.SimpleDateFormat
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

    fun updateDate(message: Message) {
        with (SimpleDateFormat("dd.mm.yyyy hh:MM")) {
            map[message.chatId]?.date = Timestamp(this.parse(message.text).time)
        }
    }

    fun updateDuration(message: Message) {
        map[message.chatId]?.duration = message.text.toInt()
        performReserve(message)
    }

    fun isReserveExist(message: Message): Boolean = map.containsKey(message.chatId)

    fun isReserveCompleted(message: Message): Boolean = hasDate(message) && hasDuration(message)

    fun hasDate(message: Message): Boolean = map[message.chatId]?.date != null

    fun hasDuration(message: Message): Boolean = map[message.chatId]?.duration != null

    private fun performReserve(message: Message) {
        reservationRepository.create(map[message.chatId]!!)
        map.remove(message.chatId)
    }


    enum class Rooms {

        BIG, SMALL

    }

}