import annotation.BotCallbackData
import annotation.BotCommand
import bot.SmlSalaryBot
import bot.Trevor
import database.DatabaseHelper
import di.AdminActionsModule
import featurecontroller.Controller
import featurecontroller.RegistrationController
import messageprocessor.MethodExecutor
import messageprocessor.MessageProcessor
import di.BotModule
import di.RegistrationModule
import di.SalaryModule
import featurecontroller.AdminActionsController
import featurecontroller.SalaryController
import org.knowm.sundial.SundialJobScheduler
import org.telegram.telegrambots.TelegramBotsApi
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class Application {
    companion object : InjektMain() {
        @JvmStatic fun main(args: Array<String>) {

            registerController(RegistrationController)
            registerController(AdminActionsController)
            registerController(SalaryController)

            DatabaseHelper.createDb()

            SundialJobScheduler.startScheduler("task")
            TelegramBotsApi().registerBot(Trevor())
        }

        override fun InjektRegistrar.registerInjectables() {
            importModule(RegistrationModule)
            importModule(AdminActionsModule)
            importModule(SalaryModule)
            importModule(BotModule)
        }

        fun registerController(controller: Controller) {
            controller.javaClass.declaredMethods
                    .forEach {
                        if (it.isAnnotationPresent(BotCommand::class.java)) {
                            val command = it.getAnnotation(BotCommand::class.java).command
                            val executor = MethodExecutor(it, controller)
                            MessageProcessor.addCommand(command, executor)
                        }
                        if (it.isAnnotationPresent(BotCallbackData::class.java)) {
                            val callbackData = it.getAnnotation(BotCallbackData::class.java).callbackData
                            val executor = MethodExecutor(it, controller)
                            MessageProcessor.addCallbackData(callbackData, executor)
                        }
                    }
        }
    }
}