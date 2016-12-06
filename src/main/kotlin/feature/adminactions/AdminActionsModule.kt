package feature.adminactions

import utils.RedisService
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object AdminActionsModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(RedisService())
        addSingleton(AdminActionsService())
    }
}