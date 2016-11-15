package user

import java.sql.ResultSet

/**
 * Created by sergeyopivalov on 15.11.16.
 */
interface Repository {

    fun create(username: String, chatId: Long)

    fun delete(chatId: Long)

    fun get(chatId: Long) : ResultSet

    fun update(chatId: Long,
               column : String,
               value : String,
               closeDb : Boolean)
}