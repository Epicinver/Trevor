import annotation.BotCommand
import bot.Trevor
import featurecontroller.Controller
import featurecontroller.RegistrationController
import messageprocessor.CommandExecutor
import messageprocessor.MessageProcessor
import org.telegram.telegrambots.TelegramBotsApi

/**
 * Created by sergeyopivalov on 09/11/2016.
 */

fun main(args: Array<String>) {
    val trevor = Trevor()

    registerController(RegistrationController(trevor))

    TelegramBotsApi().registerBot(trevor)
}

fun registerController(controller: Controller) {
    controller.javaClass.declaredMethods
            .forEach {
                if (it.isAnnotationPresent(BotCommand::class.java)) {
                    val command = it.getAnnotation(BotCommand::class.java).command
                    val executor = CommandExecutor(it, controller)
                    MessageProcessor.addCommand(command, executor)
                }
            }
}