package task

import featurecontroller.SalaryController
import org.telegram.telegrambots.api.objects.Message
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
class SalaryTask(val message: Message) : TimerTask() {

    override fun run() {
        SalaryController.notifyUserSkipTurn(message)
        SalaryController.notifyNextUser()
    }

}