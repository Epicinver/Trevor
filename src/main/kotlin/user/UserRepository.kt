package user

import database.DatabaseHelper
import java.sql.ResultSet

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object UserRepository : Repository {

    override fun create(username: String,
                        chatId: Long) {
        DatabaseHelper.
                executeTransaction("INSERT INTO users (USERNAME, CHAT_ID, ROLE) VALUES ('$username', '$chatId', 'user')")
    }

    override fun delete(chatId: Long) {
        DatabaseHelper.
                executeTransaction("DELETE FROM users WHERE CHAT_ID = $chatId")
    }

    override fun get(chatId: Long): ResultSet {
        return DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM users WHERE CHAT_ID = $chatId")
    }


    override fun update(chatId: Long, column: String, value: String, closeDb: Boolean) {
        DatabaseHelper.
                executeTransaction("UPDATE users SET $column = '$value' WHERE CHAT_ID = $chatId", closeDb)

    }

}