package messageprocessor

import featurecontroller.Controller
import org.telegram.telegrambots.api.objects.Message
import java.lang.reflect.Method

/**
 * Created by sergeyopivalov on 08/11/2016.
 */
class CommandExecutor(val method: Method, val controller: Controller) {

    fun execute(message: Message?) {
        method.invoke(controller, message)
    }
}