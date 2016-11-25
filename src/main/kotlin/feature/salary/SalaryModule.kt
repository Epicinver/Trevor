package feature.salary

import feature.salary.SalaryService
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
object SalaryModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(SalaryService())
        addSingleton(Timer())
    }
}