package messageprocessor

import featurecontroller.RegistrationController
import org.telegram.telegrambots.api.objects.Message
import java.util.*

/**
 * Created by sergeyopivalov on 09/11/2016.
 */
class MessageProcessor {

    companion object {
        private val commandsMap = HashMap<String, CommandExecutor>()

        fun addCommand(command: String, executor: CommandExecutor) {
            commandsMap.put(command, executor)
        }
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

    fun processText(message: Message) {
        with(RegistrationController) {
            if (isRegistered(message)) updateUser(message) else askPass(message)
        }
    }
}