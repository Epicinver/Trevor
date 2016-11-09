package bot

import messageprocessor.MessageProcessor
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendSticker
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.bots.TelegramLongPollingBot

/**
 * Created by sergeyopivalov on 08/11/2016.
 */
class Trevor() : TelegramLongPollingBot(), SmlSalaryBot {

    val processor = MessageProcessor()

    override fun getBotUsername(): String = "sml_testing_bot"

    override fun getBotToken(): String = "293702222:AAEhZdfDSIPuSgZ10fbvE0RNRwZLaJFGico"

    override fun onUpdateReceived(update: Update?) {
        update?.message?.let {
            processor.process(it)
        }

    }

    override fun performSendMessage(chatId: Long, text: String) {
        with(SendMessage()) {
            this.chatId = chatId.toString()
            this.text = text
            sendMessage(this)
        }
    }

    override fun performEditMessage(chatId: Long,
                                    messageId: Int,
                                    text: String,
                                    removeKeyboard: Boolean,
                                    keyboard: InlineKeyboardMarkup?) {
        with(EditMessageText()) {
            this.chatId = chatId.toString()
            this.messageId = messageId
            this.text = text
            if (removeKeyboard) {
                this.replyMarkup = null
            }
            keyboard?.let { this.replyMarkup = keyboard }
            editMessageText(this)
        }
    }

    override fun performSendSticker(chatId: Long, sticker: String) {
        with(SendSticker()) {
            this.chatId = chatId.toString()
            this.sticker = sticker
            sendSticker(this)
        }
    }
}