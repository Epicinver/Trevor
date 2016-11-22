package task

import featurecontroller.SalaryController
import org.telegram.telegrambots.api.objects.Message
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
class SalaryTask(val message : Message) : TimerTask() {

    val controller = Injekt.get<SalaryController>()

    override fun run() {
        controller.notifyUserSkipTurn(message)
    }

}