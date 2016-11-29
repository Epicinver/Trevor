package repository

import com.j256.ormlite.dao.Dao
import entity.Reservation
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 26.11.16.
 */
class ReservationRepository : Repository<Reservation> {

    val dao = Injekt.get<Dao<Reservation, Int>>()

    override fun create(reservation: Reservation) { dao.create(reservation) }

    override fun delete(id : Number) { dao.deleteById(id.toInt()) }

    override fun getById(id: Number): Reservation? = dao.queryForId(id.toInt())

    override fun getAll(): ArrayList<Reservation> = dao.queryForAll() as ArrayList<Reservation>

    override fun update(reservation: Reservation) { dao.update(reservation) }
}

