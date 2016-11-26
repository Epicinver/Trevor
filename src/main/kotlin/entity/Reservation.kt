package entity

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class Reservation (val chatId: Long,
                   val roomId: Int,
                   val time: String? = null,
                   val duration: String? = null)