package repository

import com.j256.ormlite.dao.Dao
import entity.MeetingRoom
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class MeetingRoomRepository : Repository<MeetingRoom> {

    val dao = Injekt.get<Dao<MeetingRoom, Int>>()

    override fun create(room: MeetingRoom) { dao.create(room) }

    override fun delete(id: Number) { dao.deleteById(id.toInt()) }

    override fun getById(id: Number): MeetingRoom? = dao.queryForId(id.toInt())

    override fun getAll(): ArrayList<MeetingRoom> = dao.queryForAll() as ArrayList<MeetingRoom>

    override fun update(room: MeetingRoom) { dao.update(room) }
}