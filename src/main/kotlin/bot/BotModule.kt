package bot


import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import utils.RedisService
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object BotModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingleton(Trevor() as SmlSalaryBot)
        addSingleton(JedisPoolConfig() as GenericObjectPoolConfig)
        addSingleton(JedisPool(Injekt.get(), "localhost"))
    }

}