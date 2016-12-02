package feature.base

import bot.SmlSalaryBot
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.TypeReference
import uy.kohesive.injekt.api.get
import kotlin.reflect.KClass

/**
 * Created by sergeyopivalov on 25/11/2016.
 */
//Outstanding generic injection here
abstract class BaseController<out T : BaseService>(serviceClass: KClass<T>) {
    protected val bot = Injekt.get<SmlSalaryBot>()
    protected val service: T = Injekt.get(forType = typeRef(serviceClass))

    fun <T : Any> typeRef(type: KClass<T>) = object : TypeReference<T> {
        override val type = type.java
    }

}