package feature.reservation.job

import feature.reservation.ReservationController
import org.knowm.sundial.Job
import org.knowm.sundial.annotations.CronTrigger

/**
 * Created by sergeyopivalov on 30/11/2016.
 */
@CronTrigger(jobDataMap = arrayOf("id:value"))
class ReservationCleanJob : Job() {
    override fun doRun() {
        ReservationController.cleanReservation(jobContext["id"])
    }
}