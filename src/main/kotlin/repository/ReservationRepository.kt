package repository

import database.DatabaseHelper
import entity.Reservation
import java.util.*

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationRepository : Repository<Reservation> {

    override fun create(reservation: Reservation) {
        DatabaseHelper.executeTransaction("INSERT INTO reservations (CHAT_ID, ROOM_ID) " +
                "VALUES ('${reservation.chatId}', '${reservation.roomId}')")
    }

    override fun delete(chatId: Long) {
        DatabaseHelper.executeTransaction("DELETE FROM reservations WHERE CHAT_ID = $chatId")
    }

    override fun getById(chatId: Long): Reservation? {
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM reservations WHERE CHAT_ID = $chatId")
        if (!resultSet.isBeforeFirst) {
            resultSet.close()
            return null
        }
        return with(resultSet) {
            val roomId = getInt(DatabaseHelper.COLUMN_ROOM_ID)
            val time = getString(DatabaseHelper.COLUMN_TIME)
            val duration = getString(DatabaseHelper.COLUMN_DURATION)
            resultSet.close()
            Reservation(chatId, roomId, time, duration)
        }
    }

    override fun getAll(): ArrayList<Reservation> {
        val result = ArrayList<Reservation>()
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM reservations")
        while (resultSet.next()) {
            val chatId = resultSet.getLong(DatabaseHelper.COLUMN_CHAT_ID)
            val roomId = resultSet.getInt(DatabaseHelper.COLUMN_ROOM_ID)
            val time = resultSet.getString(DatabaseHelper.COLUMN_TIME)
            val duration = resultSet.getString(DatabaseHelper.COLUMN_DURATION)
            result.add(Reservation(chatId, roomId, time, duration))
        }
        resultSet.close()
        return result
    }

    override fun update(chatId: Long, key: String, value: String, closeConnection: Boolean) {
        DatabaseHelper.
                executeTransaction("UPDATE reservations SET $key = '$value' WHERE CHAT_ID = $chatId", closeConnection)
    }
}