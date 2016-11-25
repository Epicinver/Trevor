package bot

import bot.SmlSalaryBot
import bot.Trevor
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object BotModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(Trevor() as SmlSalaryBot)
    }

}