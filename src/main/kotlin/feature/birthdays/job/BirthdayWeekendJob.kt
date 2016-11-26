package feature.birthdays.job

import feature.birthdays.BirthdayController
import org.knowm.sundial.Job
import org.knowm.sundial.annotations.CronTrigger

/**
 * Created by sergeyopivalov on 24/11/2016.
 */
@CronTrigger(cron = "0 0 10 ? * SAT-SUN")
class BirthdayWeekendJob : Job() {

    override fun doRun() {
        BirthdayController.checkBirthdays(true)
    }
}