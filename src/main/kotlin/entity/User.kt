package entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by sergeyopivalov on 16.11.16.
 */
@DatabaseTable(tableName = "users")
data class User(
        @DatabaseField var username: String? = null,
        @DatabaseField(id = true) var chatId: Long = -1,
        @DatabaseField var smlName: String? = null,
        @DatabaseField var birthday: String? = null,
        @DatabaseField val role: String = "User")

