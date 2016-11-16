package service

import entity.User
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class AdminActionsService() {

    val userRepository = Injekt.get<Repository<User>>()

    fun isAdmin(message: Message): Boolean {
        return userRepository.getById(message.chatId)?.
                role?.equals("admin") as Boolean
    }

    //todo сделать изящнее
    fun getAllNames(): ArrayList<String> {
        val result = ArrayList<String>()
        for (user in userRepository.getAll()) user.smlName?.let { result.add(it) }
        return result
    }

}