package feature.registration


import entity.User
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
    }
}