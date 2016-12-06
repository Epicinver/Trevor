package feature.salary

import entity.User
import feature.base.BaseService
import org.telegram.telegrambots.api.objects.Message
import res.Key
import utils.RedisService
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
class SalaryService : BaseService() {

    private val redisService = Injekt.get<RedisService>()

    fun storeInRedis(key: String, value : Any) { redisService.storeValue(key, value) }

    fun <T> extractFromRedis(key: String, clazz: Class<T>): T? = redisService.extractValue(key, clazz)

    fun salaryComplete() = redisService.flush()

    fun addUserToSalaryList(message: Message) =
            userRepository.getById(message.chatId)?.let {
                redisService.addToSet(Key.salaryList, it)
            }

    fun deleteUserFromSalaryList(user: User) = redisService.removeFromSet(Key.salaryList, user)

    fun getAllUsersForSalary(): ArrayList<User> = redisService.getMembersFromSet(Key.salaryList, User::class.java)

    fun getNextUser(presentUser: User?): User {
        var user = getRandomUser()
        while (user == presentUser && !isLastUser()) {
            user = getRandomUser()
        }
        return user
    }

    fun isListEmpty(): Boolean = redisService.getSizeOfSet(Key.salaryList) == 0

    private fun isLastUser(): Boolean = redisService.getSizeOfSet(Key.salaryList) == 1

    private fun getRandomUser(): User = redisService.getRandMemberFromSet(Key.salaryList, User ::class.java)


}