package feature.reservation

import entity.MeetingRoom
import entity.Reservation
import feature.base.BaseService
import feature.reservation.job.ReservationCleanJob
import org.knowm.sundial.SundialJobScheduler
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import utils.CronTriggerParser
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationService : BaseService() {

    private val reservationRepository = Injekt.get<Repository<Reservation>>()
    private val roomRepository = Injekt.get<Repository<MeetingRoom>>()
    private val map = ConcurrentHashMap<Long, Reservation>()

    fun createReserve(message: Message, room: Rooms) {
        val user = userRepository.getById(message.chatId)
        when (room) {
            Rooms.BIG -> map.put(message.chatId, Reservation(user!!, roomRepository.getById(1)!!))
            Rooms.SMALL -> map.put(message.chatId, Reservation(user!!, roomRepository.getById(2)!!))
        }
    }

    fun getAllReserves(): ArrayList<Reservation> = reservationRepository.getAll()

    fun deleteReserve(id: Int) = reservationRepository.delete(id)

    fun updateStart(message: Message) =
            with(SimpleDateFormat(ReservationController.dateFormat)) {
                map[message.chatId]?.start = this.parse(message.text).time
            }


    fun updateEnd(message: Message) {
        with(map[message.chatId]!!) {
            end = start!! + (message.text.toLong() * 60 * 1000)
        }
        performReserve(message)
    }

    fun isTimeAvailable(message: Message): Boolean {
        val date = SimpleDateFormat(ReservationController.dateFormat).parse(message.text).time
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
        SundialJobScheduler.addJob(ReservationCleanJob::class.java.simpleName,
                ReservationCleanJob::class.java,
                mapOf(Pair("id", reservation.id)),
                false)
        SundialJobScheduler.addCronTrigger("CleanReservationTrigger",
                ReservationCleanJob::class.java.simpleName,
                CronTriggerParser.parse(reservation.end!!))
    }

    enum class Rooms {

        BIG, SMALL

    }


}