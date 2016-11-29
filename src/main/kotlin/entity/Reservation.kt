package entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.sql.Timestamp

/**
 * Created by sergeyopivalov on 26.11.16.
 */
@DatabaseTable(tableName = "reservations")
class Reservation(
        @DatabaseField(generatedId = true) val id: Int? = null,
        @DatabaseField val chatId: Long? = null,
        @DatabaseField val roomId: Int? = null,
        @DatabaseField var date: Timestamp? = null,
        @DatabaseField var duration: Int? = null) {

    constructor() : this(-1, -1, -1, null, 0)
}

