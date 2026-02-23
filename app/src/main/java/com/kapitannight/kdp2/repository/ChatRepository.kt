package com.kapitannight.kdp2.repository


import android.content.Context
import com.kapitannight.kdp2.data.DatabaseProvider
import com.kapitannight.kdp2.data.model.Chat
import com.kapitannight.kdp2.data.model.Message
import com.kapitannight.kdp2.data.dao.*
import com.kapitannight.kdp2.data.model.ChatEntity
import com.kapitannight.kdp2.data.model.MessageEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.String

object ChatRepository {

    private lateinit var chatDao: ChatDao
    private lateinit var messageDao: MessageDao
    suspend fun getChatsFromDb(): List<Chat>{
        val getChat = chatDao.getChats().first()
        .map {entity ->
            Chat(
                id = entity.id,
                name = entity.name,
                lastMessage = entity.lastMessage,
                timestamp = entity.timestamp,
                isOnline = entity.isOnline,
                content = entity.content
            )
        }

        return getChat
    }
    suspend fun getMessagesFromDb(chatId: String): List<Message>{
        val getMessage = messageDao.getMessages(chatId).first()
            .map{entity ->
                Message(
                    id = entity.id,
                    chatId = entity.chatId,
                    sender = entity.sender,
                    content = entity.content,
                    timestamp = entity.timestamp
                )
            }
        return getMessage
    }

    suspend fun seedDatabase(){
        if (chatDao.getChats().first().isEmpty()) {
            // 1. Создаём чаты (с пустым lastMessage)
            val chat0 = ChatEntity(
                id = "0",
                name = "Избранное",
                lastMessage = "",
                timestamp = System.currentTimeMillis(),
                isOnline = false,
                content = ""
            )
            val chat1 = ChatEntity(
                id = "1",
                name = "Fomalhaut",
                lastMessage = "",
                timestamp = System.currentTimeMillis(),
                isOnline = false,
                content = ""
            )
            val chat2 = ChatEntity(
                id = "2",
                name = "Сергей",
                lastMessage = "",
                timestamp = System.currentTimeMillis(),
                isOnline = true,
                content = ""
            )

            // Вставляем чаты
            chatDao.addChat(chat0)
            chatDao.addChat(chat1)
            chatDao.addChat(chat2)


        }
    }

    suspend fun addMessageToDb(chatId: String, message: Message){
        println("📤 Отправка сообщения в чат: $chatId")

        val messageEntity = MessageEntity(
            id = message.id,
            chatId = chatId,
            sender = message.sender,
            content = message.content,
            timestamp = message.timestamp
        )

        messageDao.sendMessage(messageEntity)
        println("✅ Сообщение сохранено в БД")

        val chat = chatDao.getId(chatId).first()
        println("📊 Чат из базы: $chat")

        if (chat != null) {
            println("🔄 Обновляем чат с lastMessage: ${message.content}")
            chatDao.updateChat(chat.copy(
                lastMessage = message.content,
                timestamp = message.timestamp
            ))
            println("✅ Чат обновлён!")
        } else {
            println("❌ Чат не найден!")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun init(context: Context){
        val database = DatabaseProvider.getDatabase(context)
        chatDao = database.chatDao()
        messageDao = database.messageDao()
        kotlinx.coroutines.GlobalScope.launch {
            seedDatabase()
        }
    }
}