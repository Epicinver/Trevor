package feature.adminactions

import entity.User
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import feature.base.BaseService
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class AdminActionsService : BaseService() {

    fun isAdmin(message: Message): Boolean {
        return userRepository.getById(message.chatId)?.
                role == ("admin")
    }

    fun deleteUser(chatId: Long) {
        userRepository.delete(chatId)
    }

    fun getHelper(): User {
        return userRepository.getAll()
                .filter { it.role == "911" }[0]
    }

    fun getAllUsers(): ArrayList<User> {
        return userRepository.getAll()
    }


}