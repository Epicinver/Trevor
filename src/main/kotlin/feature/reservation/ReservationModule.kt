package feature.reservation

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import entity.MeetingRoom
import entity.Reservation
import repository.Repository
import repository.ReservationRepository
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton

/**
 * Created by sergeyopivalov on 26.11.16.
 */
object ReservationModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(ReservationRepository() as Repository<Reservation>)
        addSingleton(ReservationService())
        addSingleton(DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), MeetingRoom::class.java) as
                Dao<*, *>)
        addSingleton(DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), Reservation::class.java) as
                Dao<*, *>)
    }
}