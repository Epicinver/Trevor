package feature.reservation.job

import feature.reservation.ReservationController
import org.knowm.sundial.Job

/**
 * Created by sergeyopivalov on 30/11/2016.
 */
class ReservationCleanJob : Job() {

    override fun doRun() {
        ReservationController.cleanReservation()
    }
}