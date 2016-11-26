package repository

import database.DatabaseHelper
import entity.MeetingRoom
import entity.Reservation
import java.util.*

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class MeetingRoomRepository : Repository<MeetingRoom>{

    override fun create(room: MeetingRoom) {
        DatabaseHelper.executeTransaction("INSERT INTO rooms (DESCRIPTION) " +
                "VALUES ('${room.description}')")
    }

    override fun delete(id: Long) {
        DatabaseHelper.executeTransaction("DELETE FROM rooms WHERE ROOM_ID = $id")

    }

    override fun getById(id: Long): MeetingRoom? {
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM rooms WHERE ROOM_ID = $id")
        if (!resultSet.isBeforeFirst) {
            resultSet.close()
            return null
        }
        return with(resultSet) {
            val description = getString(DatabaseHelper.COLUMN_DESCRIPTION)
            resultSet.close()
            MeetingRoom(description)
        }
    }

    override fun getAll(): ArrayList<MeetingRoom> {
        val result = ArrayList<MeetingRoom>()
        val resultSet = DatabaseHelper.getConnection().
                createStatement().executeQuery("SELECT * FROM rooms")
        while (resultSet.next()) {
            val description = resultSet.getString(DatabaseHelper.COLUMN_DESCRIPTION)
            result.add(MeetingRoom(description))
        }
        resultSet.close()
        return result
    }

    override fun update(id: Long, key: String, value: String, closeConnection: Boolean) {
        DatabaseHelper.
                executeTransaction("UPDATE rooms SET $key = '$value' WHERE ROOM_ID = $id", closeConnection)
    }
}