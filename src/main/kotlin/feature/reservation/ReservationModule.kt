package feature.reservation

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

    }
}