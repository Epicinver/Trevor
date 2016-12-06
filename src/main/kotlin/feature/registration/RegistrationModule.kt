package feature.registration


import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import entity.User
import repository.Repository
import repository.UserRepository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object RegistrationModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(DaoManager.createDao(Injekt.get(), User::class.java) as Dao<User, Long>)
        addSingleton(UserRepository() as Repository<User>)
        addSingleton(RegistrationService())
    }
}