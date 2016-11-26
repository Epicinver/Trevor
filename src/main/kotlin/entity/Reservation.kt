package entity

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class Reservation (val reservatorChatId: Int,
                  val meetingRoomId: Int,
                  val time: String? = null,
                  val reservationTime: String? = null)