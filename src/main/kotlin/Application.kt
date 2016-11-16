import annotation.BotCommand
import bot.SmlSalaryBot
import bot.Trevor
import database.DatabaseHelper
import featurecontroller.Controller
import featurecontroller.RegistrationController
import messageprocessor.CommandExecutor
import messageprocessor.MessageProcessor
import di.BotModule
import di.RegistrationModule
import org.telegram.telegrambots.TelegramBotsApi
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class Application {
    companion object : InjektMain() {
        @JvmStatic fun main(args: Array<String>) {

            registerController(RegistrationController)

            DatabaseHelper.createDb()

            TelegramBotsApi().registerBot(Trevor())
        }

        override fun InjektRegistrar.registerInjectables() {
            importModule(RegistrationModule)
            importModule(BotModule)
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
    }
}