package bot

import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup

/**
 * Created by sergeyopivalov on 09/11/2016.
 */
interface SmlSalaryBot {

    fun performSendMessage(chatId : Long,
                           text : String,
                           keyboard: InlineKeyboardMarkup? = null,
                           forceReply:Boolean? = null) : Message


    fun performEditMessage(chatId: Long,
                           messageId : Int,
                           text: String,
                           removeKeyboard : Boolean = false)

    fun performEditKeyboard(chatId: Long,
                            messageId: Int,
                            keyboard: InlineKeyboardMarkup)

    fun performSendSticker(chatId: Long, sticker: String)
}