package registration

import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import user.UserRepository

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object RegistrationService {

    fun alreadyRegistered(message: Message): Boolean =
            UserRepository.get(message.chatId).isBeforeFirst

    fun acceptRegistration(message: Message) {
        UserRepository.create(message.from.userName, message.chatId)
    }

    fun updateUsername(chatId: Long, username: String) {
        UserRepository.updateUsername(chatId, username)
    }

    fun updateSmlName(chatId: Long, smlName : String) {
        UserRepository.updateSmlName(chatId, smlName)
    }

    fun updateBirtday(chatId: Long, birthday :String) {
        UserRepository.updateBirthday(chatId, birthday)
    }

    fun hasSmlName(message: Message): Boolean {
        return with(UserRepository.get(message.chatId)) {
            TextUtils.isEmpty(this?.getString("SML_NAME"))
        }
    }

    fun hasBirthday(message: Message): Boolean {
        return with(UserRepository.get(message.chatId)) {
            TextUtils.isEmpty(this?.getString("BIRTHDAY"))
        }
    }
}