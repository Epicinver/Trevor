package user

import database.DatabaseHelper
import java.sql.ResultSet

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object UserRepository : Repository {
    private fun executeTransaction(transaction : String, closeDb : Boolean = false) {
        with(DatabaseHelper.openDb()) {
            with(createStatement()) {
                executeUpdate(transaction)
                commit()
            }
            if (closeDb) DatabaseHelper.closeDb()
        }
    }

    override fun create(username: String,
                        chatId: Long) {
        executeTransaction("INSERT INTO users (USERNAME, CHAT_ID, ROLE) VALUES ('$username', '$chatId', 'user')")
    }

    override fun delete(chatId: Long) {
        executeTransaction("DELETE FROM users WHERE CHAT_ID = $chatId")
    }

    override fun get(chatId: Long): ResultSet {
        return with(DatabaseHelper.openDb()) {
            createStatement().executeQuery("SELECT * FROM users WHERE CHAT_ID = $chatId")
        }
    }

    override fun update(chatId: Long, column: String, value: String, closeDb: Boolean) {
        executeTransaction("UPDATE users SET $column = '$value' WHERE CHAT_ID = $chatId", closeDb)

    }

}