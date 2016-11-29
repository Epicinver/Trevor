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
    @DatabaseField(foreign = true, foreignAutoRefresh = true) var user: User? = null
    @DatabaseField(foreign = true, foreignAutoRefresh = true) var room: MeetingRoom? = null
    @DatabaseField var date: Long? = null
    @DatabaseField var duration: Int? = null

    constructor() {
    }

    constructor(user: User, room: MeetingRoom) {
        this.user = user
        this.room = room
    }

}

