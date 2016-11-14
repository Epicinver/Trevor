package user

import database.DatabaseHelper
import database.DatabaseRepository
import java.sql.ResultSet

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object UserRepository : DatabaseRepository() {

    fun create(username: String,
               chatId: Long,
               smlName: String? = null,
               birthday: String? = null) {
        executeTransaction("INSERT INTO users VALUES ('$username', $chatId, '$smlName', '$birthday', 'user')")
    }

    fun delete(username: String) {
        executeTransaction("DELETE FROM users WHERE = '$username'")
    }

    //todo закрывать стейтменты желательно как бэ
    fun get(chatId: Long) : ResultSet {
        with(DatabaseHelper.openDb()) {
           val result = with(createStatement()) {
               executeQuery("SELECT * FROM users WHERE CHAT_ID = $chatId")
            }
            close()
            return@get result
        }
    }

    fun updateUsername(chatId: Long, username: String) {
        executeTransaction("UPDATE users SET USERNAME = '$username' WHERE CHAT_ID = $chatId")
    }

    fun updateSmlName(chatId: Long, smlName: String) {
        executeTransaction("UPDATE users SET SML_NAME = '$smlName' WHERE CHAT_ID = $chatId")
    }

    fun updateBirthday(chatId: Long, birthday: String) {
        executeTransaction("UPDATE users SET BIRTHDAY = $birthday WHERE CHAT_ID = $chatId")
    }

}