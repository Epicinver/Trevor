package entity

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by sergeyopivalov on 16.11.16.
 */
@DatabaseTable(tableName = "users")
class User(@DatabaseField var username: String,
           @DatabaseField(id = true) val chatId: Long,
           @DatabaseField var smlName: String? = null,
           @DatabaseField var birthday: String? = null,
           @DatabaseField var role: String = "User") {
    constructor() : this("", -1, "", "", "")
}
