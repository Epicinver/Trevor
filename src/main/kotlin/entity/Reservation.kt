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
        @DatabaseField(foreign = true) val chatId: Long? = null,
        @DatabaseField(foreign = true) val roomId: Int? = null,
        @DatabaseField val date: Timestamp? = null,
        @DatabaseField val duration: Long? = null)

