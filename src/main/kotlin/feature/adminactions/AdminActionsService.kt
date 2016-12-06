package feature.adminactions

import entity.User
import feature.base.BaseService
import org.telegram.telegrambots.api.objects.Message
import res.Key
import utils.RedisService
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class AdminActionsService : BaseService() {

    private val redisService = Injekt.get<RedisService>()

    fun isAdmin(message: Message): Boolean = userRepository.getById(message.chatId)?.role == ("Admin")

    fun deleteUser(chatId: Long) = userRepository.delete(chatId)

    fun getHelper(): User = userRepository.getAll()
            .filter { it.role == "911" }[0]

    fun getAllUsers(): ArrayList<User> = userRepository.getAll()

    fun storeMessage(message: Message) { redisService.storeValue(Key.messageActions, message) }

    fun extractMessage(): Message =  redisService.extractValue(Key.messageActions, Message::class.java)!!
}