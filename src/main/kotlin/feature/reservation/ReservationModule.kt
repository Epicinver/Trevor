package feature.reservation

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import entity.MeetingRoom
import entity.Reservation
import repository.MeetingRoomRepository
import repository.Repository
import repository.ReservationRepository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 26.11.16.
 */
object ReservationModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(DaoManager.createDao(Injekt.get(), Reservation::class.java) as Dao<Reservation, Int>)
        addSingleton(DaoManager.createDao(Injekt.get(), MeetingRoom::class.java) as Dao<MeetingRoom, Int>)
        addSingleton(ReservationRepository() as Repository<Reservation>)
        addSingleton(MeetingRoomRepository() as Repository<MeetingRoom>)
        addSingleton(ReservationService())
    }


}