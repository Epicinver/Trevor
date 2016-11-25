package bot

import processor.MessageProcessor
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendSticker
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import utils.PropertiesLoader

/**
 * Created by sergeyopivalov on 08/11/2016.
 */
class Trevor : TelegramLongPollingBot(), SmlSalaryBot {

    override fun getBotUsername(): String =
            PropertiesLoader.getProperty("bot.username")

    override fun getBotToken(): String =
            PropertiesLoader.getProperty("bot.token")

    override fun onUpdateReceived(update: Update?) {
        update?.message?.let {
            MessageProcessor.processCommand(it)
        }
        update?.callbackQuery?.let {
            MessageProcessor.processCallbackQuery(it)
        }

    }

    //todo forceReply тут кажется уже лишним. Может вынести в отдельный метод может ?
    override fun performSendMessage(chatId: Long,
                                    text: String,
                                    keyboard: InlineKeyboardMarkup?,
                                    forceReply : Boolean?): Message {
        return with(SendMessage()) {
            this.chatId = chatId.toString()
            this.text = text
            forceReply?.let { this.replyMarkup = ForceReplyKeyboard()}
            keyboard?.let { this.replyMarkup = keyboard }
            sendMessage(this)
        }
    }

    override fun performEditMessage(chatId: Long,
                                    messageId: Int,
                                    text: String,
                                    removeKeyboard: Boolean) {
        with(EditMessageText()) {
            this.chatId = chatId.toString()
            this.messageId = messageId
            this.text = text
            if (removeKeyboard) {
                this.replyMarkup = null
            }
            editMessageText(this)
        }
    }

    override fun performEditKeyboard(chatId: Long, messageId: Int, keyboard: InlineKeyboardMarkup) {
        with(EditMessageReplyMarkup()) {
            this.chatId = chatId.toString()
            this.messageId = messageId
            this.replyMarkup = keyboard
            editMessageReplyMarkup(this)
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