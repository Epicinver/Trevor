package entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by sergeyopivalov on 26.11.16.
 */
@DatabaseTable(tableName = "rooms")
class MeetingRoom() {
    @DatabaseField(generatedId = true) val id: Int = -1
    @DatabaseField val description: String = ""
}