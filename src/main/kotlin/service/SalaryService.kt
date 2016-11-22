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
class SalaryService {

    private val salaryList = ArrayList<User>()

    val userRepository = Injekt.get<Repository<User>>()

    fun addUserToSalaryList(message: Message) {
        userRepository.getById(message.chatId)?.let {
            salaryList.add(it)
        }
    }

    fun deleteUserFromSalaryList(message: Message) {
        userRepository.getById(message.chatId)?.let {
            salaryList.remove(it)
        }
    }

    fun getAllUsersForSalary(): ArrayList<User> = salaryList

    fun getUserForSalary(): User {
        return Random().nextInt(salaryList.size).let {
            salaryList[it]
        }
    }

    fun getSalaryListSize(): Int = salaryList.size
}