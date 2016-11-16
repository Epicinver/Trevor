package registration

import entity.User
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import repository.UserRepository

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object RegistrationService {

    //todo вызывать методы у Repository
    fun isExist(message: Message): Boolean =
            UserRepository.getById(message.chatId) != null

    fun isRegistrationCompleted(message: Message): Boolean =
            hasSmlName(message) && hasBirthday(message)

    fun createUser(message: Message) {
        UserRepository.create(User(message.from.userName, message.chatId))
    }

    fun updateUser(chatId : Long, key: String, value: String, closeDb: Boolean = false) {
        UserRepository.update(chatId, key, value, closeDb)
    }

    fun hasSmlName(message: Message): Boolean {
        return with(UserRepository.getById(message.chatId)) {
            !TextUtils.isEmpty(this?.smlName)
        }
    }

    fun hasBirthday(message: Message): Boolean {
        return with(UserRepository.getById(message.chatId)) {
            !TextUtils.isEmpty(this?.birthday)
        }
    }
}