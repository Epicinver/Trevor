package feature.reservation

import entity.MeetingRoom
import entity.Reservation
import repository.MeetingRoomRepository
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
        addSingleton(MeetingRoomRepository() as Repository<MeetingRoom>)
        addSingleton(ReservationService())
//        addSingleton(DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), MeetingRoom::class.java) as
//                Dao<*, *>)
//        addSingleton(DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), Reservation::class.java) as
//                Dao<*, *>)
    }
}