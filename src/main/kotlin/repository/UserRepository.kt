package repository

import database.DatabaseHelper
import entity.User

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object UserRepository : Repository <User> {

    override fun create(user: User) {
        DatabaseHelper.
                executeTransaction("INSERT INTO users (USERNAME, CHAT_ID, ROLE) " +
                        "VALUES ('${user.username}', '${user.chatId}', 'user')")
    }

    override fun delete(chatId: Long) {
        DatabaseHelper.
                executeTransaction("DELETE FROM users WHERE CHAT_ID = $chatId")
    }

    override fun getById(chatId: Long): User? {
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM users WHERE CHAT_ID = $chatId")
        if (!resultSet.isBeforeFirst) return null
        return with(resultSet) {
            val username = getString(DatabaseHelper.COLUMN_USERNAME)
            val smlName = getString(DatabaseHelper.COLUMN_SML_NAME)
            val birthday = getString(DatabaseHelper.COLUMN_BIRTHDAY)
            User(username, chatId, smlName, birthday)
        }
    }

    override fun update(chatId: Long, key: String, value: String, closeDb: Boolean) {
        DatabaseHelper.
                executeTransaction("UPDATE users SET $key = '$value' WHERE CHAT_ID = $chatId", closeDb)

    }

}