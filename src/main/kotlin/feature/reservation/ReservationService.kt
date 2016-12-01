package feature.reservation

import entity.MeetingRoom
import entity.Reservation
import feature.base.BaseService
import feature.reservation.job.ReservationCleanJob
import org.knowm.sundial.SundialJobScheduler
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationService : BaseService() {

    val reservationRepository = Injekt.get<Repository<Reservation>>()
    val roomRepository = Injekt.get<Repository<MeetingRoom>>()
    val map = ConcurrentHashMap<Long, Reservation>()

    fun createReserve(message: Message, room: Rooms) {
        val user = userRepository.getById(message.chatId)
        when (room) {
            Rooms.BIG -> map.put(message.chatId, Reservation(user!!, roomRepository.getById(1)!!))
            Rooms.SMALL -> map.put(message.chatId, Reservation(user!!, roomRepository.getById(2)!!))
        }
    }

    fun getAllReserves(): ArrayList<Reservation> = reservationRepository.getAll()

    fun deleteReserve(id: Int) {
        reservationRepository.delete(id)
    }

    fun updateStart(message: Message) {
        with(SimpleDateFormat("dd.MM.yyyy hh:mm")) {
            map[message.chatId]?.start = this.parse(message.text).time
        }
    }

    fun updateEnd(message: Message) {
        with(map[message.chatId]!!) {
            end = start!! + (message.text.toLong() * 60 * 1000)
        }
        performReserve(message)
    }

    fun isTimeAvailable(message: Message): Boolean {
        val date = SimpleDateFormat("dd.MM.yyyy hh:mm").parse(message.text).time
        return if (date < System.currentTimeMillis()) false else
            reservationRepository.getAll()
                    .filter { date in it.start!!..it.end!! }
                    .isEmpty()
    }

    fun isReserveExist(message: Message): Boolean = map.containsKey(message.chatId)

    fun isReserveCompleted(message: Message): Boolean = hasStart(message) && hasEnd(message)

    fun hasStart(message: Message): Boolean = map[message.chatId]?.start != null

    fun hasEnd(message: Message): Boolean = map[message.chatId]?.end != null

    private fun performReserve(message: Message) {
        createCleanJob(reservationRepository.create(map[message.chatId]!!))
        map.remove(message.chatId)
    }

    private fun createCleanJob(reservation: Reservation) {
        val endReservation = Calendar.getInstance().apply {
            time = Date(reservation.end!!)
        }
        SundialJobScheduler.addJob(ReservationCleanJob::class.java.simpleName,
                ReservationCleanJob::class.java,
                mapOf(Pair("id", reservation.id)),
                false)
        SundialJobScheduler.addCronTrigger("CleanReservationTrigger", ReservationCleanJob::class.java.simpleName,
                "0 ${endReservation.get(Calendar.MINUTE)} " +
                        "${endReservation.get(Calendar.HOUR_OF_DAY)} " +
                        "${endReservation.get(Calendar.DAY_OF_MONTH)} " +
                        "${endReservation.get(Calendar.MONTH)} ? " +
                        "${endReservation.get(Calendar.YEAR)}")
    }

    enum class Rooms {

        BIG, SMALL

    }


}