package registration

import org.apache.http.util.TextUtils
import org.telegram.telegrambots.api.objects.Message
import user.UserRepository

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
object RegistrationService {

    //todo вызывать методы у Repository
    fun isExist(message: Message): Boolean =
            UserRepository.get(message.chatId).isBeforeFirst

    fun isRegistrationCompleted(message: Message): Boolean  =
            hasSmlName(message) && hasBirtday(message)

    fun createUser(message: Message) {
        UserRepository.create(message.from.userName, message.chatId)
    }

    fun updateUser (chatId: Long, column : String, value : String, closeDb : Boolean = false) {
        UserRepository.update(chatId,column,value, closeDb)
    }

    fun hasSmlName(message: Message): Boolean {
        return with(UserRepository.get(message.chatId)) {
            !TextUtils.isEmpty(this.getString("SML_NAME"))
        }
    }

    fun hasBirtday(message: Message): Boolean {
        return with(UserRepository.get(message.chatId)) {
            !TextUtils.isEmpty(this.getString("BIRTHDAY"))
        }
    }
}