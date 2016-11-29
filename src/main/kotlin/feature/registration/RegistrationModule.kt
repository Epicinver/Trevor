package feature.registration


import bot.SmlSalaryBot
import bot.Trevor
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import entity.Reservation
import entity.User
import feature.registration.RegistrationService
import repository.Repository
import repository.UserRepository
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object RegistrationModule: InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(UserRepository() as Repository<User>)
        addSingleton(RegistrationService())
        addSingleton(DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), User::class.java) as
                Dao<*, *>)
    }
}