package processor

import feature.base.BaseController
import org.telegram.telegrambots.api.objects.Message
import java.lang.reflect.Method

/**
 * Created by sergeyopivalov on 08/11/2016.
 */
class MethodExecutor(val method: Method, val controller: BaseController) {

    fun execute(message: Message?) {
        method.invoke(controller, message)
    }
}