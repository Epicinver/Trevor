package repository

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.stmt.UpdateBuilder
import entity.MeetingRoom
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class MeetingRoomRepository : Repository<MeetingRoom> {

    //    val dao = Injekt.get<Dao<MeetingRoom, Int>>()
    val dao: Dao<MeetingRoom, Int> = DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), MeetingRoom::class.java)

    override fun create(room: MeetingRoom): MeetingRoom = dao.createIfNotExists(room)

    override fun delete(id: Number) { dao.deleteById(id.toInt()) }

    override fun getById(id: Number): MeetingRoom? = dao.queryForId(id.toInt())

    override fun getAll(): ArrayList<MeetingRoom> = dao.queryForAll() as ArrayList<MeetingRoom>

    override fun update(id: Number, column: String, value: Any) {
        dao.updateBuilder().apply {
            where().eq("id", id)
            updateColumnValue(column, value)
            update()
        }
    }
}