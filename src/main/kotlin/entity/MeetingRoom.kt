package entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by sergeyopivalov on 26.11.16.
 */
@DatabaseTable(tableName = "rooms") //TODO переделать сущности
class MeetingRoom(@DatabaseField(generatedId = true) val id: Int,
                  @DatabaseField val description: String) {
    constructor() : this(-1, "") {

    }
}