package utils

import com.fasterxml.jackson.databind.ObjectMapper
import redis.clients.jedis.JedisPool
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 05/12/2016.
 */
class RedisService {

    private val jedisPool = Injekt.get<JedisPool>()

    fun addToSet(key: String, value: Any) {
        jedisPool.resource.use { it.sadd(key, ObjectMapper().writeValueAsString(value)) }
    }

    fun removeFromSet(key: String, value: Any) {
        jedisPool.resource.use { it.srem(key, ObjectMapper().writeValueAsString(value)) }
    }

    fun <T> getMembersFromSet(key: String, clazz: Class<T>): ArrayList<T> =
            jedisPool.resource.use {
                it.smembers(key).
                        map { ObjectMapper().readValue(it, clazz) }.
                        toCollection(ArrayList<T>())
            }

    fun <T> getRandMemberFromSet(key: String, clazz: Class<T>): T =
            jedisPool.resource.use {
                ObjectMapper().readValue(it.srandmember(key), clazz)
            }

    fun getSizeOfSet(key: String): Int? = jedisPool.resource.use { it.scard(key).toInt() }

    fun storeValue(key: String, value: Any) {
        jedisPool.resource.use { it.set(key, ObjectMapper().writeValueAsString(value)) }
    }

    fun <T> extractValue(key: String, clazz: Class<T>): T? =
            jedisPool.resource.use { ObjectMapper().readValue(it.get(key), clazz) }

    fun flush() = jedisPool.resource.use { it.flushAll()}

}