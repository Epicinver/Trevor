package repository

import database.DatabaseHelper
import entity.User
import java.sql.ResultSet
import java.util.*

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
class UserRepository : Repository <User> {
    override fun create(user: User) {
        DatabaseHelper.
                executeTransaction("INSERT INTO users (USERNAME, CHAT_ID, ROLE) " +
                        "VALUES ('${user.username}', '${user.chatId}', '${user.role}')")
    }

    override fun delete(chatId: Long) {
        DatabaseHelper.executeTransaction("DELETE FROM users WHERE CHAT_ID = $chatId")
    }

    override fun getById(chatId: Long): User? {
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM users WHERE CHAT_ID = $chatId")
        if (!resultSet.isBeforeFirst) {
            resultSet.close()
            return null
        }
        return with(resultSet) {
            val username = getString(DatabaseHelper.COLUMN_USERNAME)
            val smlName = getString(DatabaseHelper.COLUMN_SML_NAME)
            val birthday = getString(DatabaseHelper.COLUMN_BIRTHDAY)
            val role = getString(DatabaseHelper.COLUMN_ROLE)
            resultSet.close()
            User(username, chatId, smlName, birthday, role)
        }
    }

    override fun getAll(): ArrayList<User> {
        val result  = ArrayList<User>()
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM users")
        while (resultSet.next()) {
            val username = resultSet.getString(DatabaseHelper.COLUMN_USERNAME)
            val chatId = resultSet.getLong(DatabaseHelper.COLUMN_CHAT_ID)
            val smlName = resultSet.getString(DatabaseHelper.COLUMN_SML_NAME)
            val birthday = resultSet.getString(DatabaseHelper.COLUMN_BIRTHDAY)
            val role = resultSet.getString(DatabaseHelper.COLUMN_ROLE)
            result.add(User(username, chatId, smlName, birthday, role))
        }
        resultSet.close()
        return result
    }

    override fun update(chatId: Long, key: String, value: String, closeConnection: Boolean) {
        DatabaseHelper.
                executeTransaction("UPDATE users SET $key = '$value' WHERE CHAT_ID = $chatId", closeConnection)

    }

}