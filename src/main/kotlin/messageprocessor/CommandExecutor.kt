package messageprocessor

import featurecontroller.Controller
import java.lang.reflect.Method

/**
 * Created by sergeyopivalov on 08/11/2016.
 */
class CommandExecutor(val method: Method, val controller: Controller) {

    fun execute(chatId: Long?) {
        method.invoke(controller, chatId)
    }
}