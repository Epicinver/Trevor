package messageprocessor

import featurecontroller.RegistrationController
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.Message
import java.util.*

/**
 * Created by sergeyopivalov on 09/11/2016.
 */
object MessageProcessor {

    private val commandsMap = HashMap<String, MethodExecutor>()
    private val callbackDataMap = HashMap<String, MethodExecutor>()

    fun addCommand(command: String, executor: MethodExecutor) {
        commandsMap.put(command, executor)
    }

    fun addCallbackData(callbackData: String, executor: MethodExecutor) {
        callbackDataMap.put(callbackData, executor)
    }


    fun processCommand(message: Message) {
        message.text?.let {
            if (it in commandsMap) {
                commandsMap[it]?.execute(message)
                return
            } else {
                processText(message)
            }
        }
    }

    fun processCallbackQuery(query: CallbackQuery) {
        query.data?.let {
            if (it in callbackDataMap) {
                callbackDataMap[it]?.execute(query.message)
            }
        }

    }

    private fun processText(message: Message) {
        with(RegistrationController) {
            if (isRegistered(message)) updateUser(message) else askPass(message)
        }
    }
}