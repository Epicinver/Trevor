package service

import entity.User
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
class RegistrationService() {
    val repository: Repository<User> = Injekt.get()

    fun isExist(message: Message): Boolean =
            repository.getById(message.chatId) != null

    fun isRegistrationCompleted(message: Message): Boolean =
            hasSmlName(message) && hasBirthday(message)

    fun createUser(message: Message) {
        repository.create(User(message.from.userName, message.chatId, role = "user"))
    }

    fun updateUser(chatId: Long, key: String, value: String, closeDb: Boolean = false) {
        repository.update(chatId, key, value, closeDb)
    }

    fun hasSmlName(message: Message): Boolean {
        return with(repository.getById(message.chatId)) {
            !TextUtils.isEmpty(this?.smlName)
        }
    }

    fun hasBirthday(message: Message): Boolean {
        return with(repository.getById(message.chatId)) {
            !TextUtils.isEmpty(this?.birthday)
        }
    }
}