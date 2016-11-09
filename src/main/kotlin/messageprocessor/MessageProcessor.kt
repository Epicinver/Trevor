package messageprocessor

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

    fun process(message: Message) {
        message.text?.let {
            if (it in commandsMap) commandsMap[it]?.execute(message.chatId)
        }
    }


}