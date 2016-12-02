package feature.registration

import entity.User
import feature.base.BaseService
import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
class RegistrationService : BaseService() {

    fun isUserExist(message: Message): Boolean = userRepository.getById(message.chatId) != null

    fun isRegistrationCompleted(message: Message): Boolean = hasSmlName(message) && hasBirthday(message)

    fun createUser(message: Message) = userRepository.create(User(message.from.userName, message.chatId))

    fun updateUser(chatId: Long, column: String, value: Any) = userRepository.update(chatId, column, value)

    fun hasSmlName(message: Message): Boolean =
            with(userRepository.getById(message.chatId)) {
                !TextUtils.isEmpty(this?.smlName)
            }

    private fun hasBirthday(message: Message): Boolean =
            with(userRepository.getById(message.chatId)) {
                !TextUtils.isEmpty(this?.birthday)
            }

}