import annotation.BotCallbackData
import annotation.BotCommand
import bot.BotModule
import bot.Trevor
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import entity.MeetingRoom
import entity.Reservation
import entity.User
import feature.adminactions.AdminActionsController
import feature.adminactions.AdminActionsModule
import feature.base.BaseController
import feature.birthdays.BirthdayController
import feature.birthdays.BirthdayModule
import feature.registration.RegistrationController
import feature.registration.RegistrationModule
import feature.reservation.ReservationController
import feature.reservation.ReservationModule
import feature.salary.SalaryController
import feature.salary.SalaryModule
import org.knowm.sundial.SundialJobScheduler
import org.telegram.telegrambots.TelegramBotsApi
import processor.MessageProcessor
import processor.MethodExecutor
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
            registerController(BirthdayController)
            registerController(ReservationController)

            initDb()

            TelegramBotsApi().registerBot(Trevor())

            SundialJobScheduler.startScheduler("feature.birthdays.job")
        }

        override fun InjektRegistrar.registerInjectables() {
            importModule(BotModule)
            importModule(RegistrationModule)
            importModule(AdminActionsModule)
            importModule(SalaryModule)
            importModule(BirthdayModule)
            importModule(ReservationModule)
        }

        private fun initDb() {
            val connection = JdbcConnectionSource("jdbc:sqlite:test.s3db")
            TableUtils.createTableIfNotExists(connection, User::class.java)
            TableUtils.createTableIfNotExists(connection, MeetingRoom::class.java)
            TableUtils.createTableIfNotExists(connection, Reservation::class.java)
        }

        private fun registerController(controller: BaseController) {
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