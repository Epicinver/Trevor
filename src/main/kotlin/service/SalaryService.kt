package service

import entity.User
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 20.11.16.
 */
class SalaryService() {

    private val salaryList = ArrayList<User>()

    val userRepository = Injekt.get<Repository<User>>()

    fun addUserToSalaryList(message: Message) {
        userRepository.getById(message.chatId)?.let {
            salaryList.add(it)
        }
    }

    fun deleteUserFromSalaryList(user : User) {
        salaryList.remove(user)
    }

    fun getAllUsersForSalary(): ArrayList<User> = salaryList

    fun getNextUser(presentUser: User?) : User {
        var user = getRandomUser()
        while (user == presentUser && !isLastUser()) {
            user = getRandomUser()
        }
        return user
    }

    fun isListEmpty(): Boolean = salaryList.size == 0

    private fun isLastUser(): Boolean = salaryList.size == 1

    private fun getRandomUser(): User {
        return Random().nextInt(salaryList.size).let {
            salaryList[it]
        }
    }
}