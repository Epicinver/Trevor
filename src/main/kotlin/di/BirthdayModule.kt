package di

import service.BirthdayService
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton

/**
 * Created by sergeyopivalov on 24/11/2016.
 */
object BirthdayModule : InjektModule{
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(BirthdayService())
    }
}