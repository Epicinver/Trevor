package repository

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import entity.Reservation
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationRepository : Repository<Reservation> {

    val dao = Injekt.get<Dao<Reservation, Int>>()

    override fun create(reservation: Reservation) : Reservation =  dao.createIfNotExists(reservation)

    override fun delete(id: Number) { dao.deleteById(id.toInt()) }

    override fun getById(id: Number): Reservation? = dao.queryForId(id.toInt())

    override fun getAll(): ArrayList<Reservation> = dao.queryForAll() as ArrayList<Reservation>

    override fun update(id: Number, column: String, value: Any) {
        dao.updateBuilder().apply {
            where().eq("id", id)
            updateColumnValue(column, value)
            update()
        }
    }
}

