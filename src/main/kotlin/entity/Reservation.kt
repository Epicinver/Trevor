package entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.sql.Timestamp

/**
 * Created by sergeyopivalov on 26.11.16.
 */
@DatabaseTable(tableName = "reservations")
class Reservation {
    @DatabaseField(generatedId = true) val id: Int = -1
    @DatabaseField var chatId: Long = -1
    @DatabaseField var roomId: Int = -1
    @DatabaseField var date: Long? = -1
    @DatabaseField var duration: Int? = -1

    constructor() {
    }

    constructor(chatId: Long, roomId: Int) {
        this.chatId = chatId
        this.roomId = roomId
    }

}

