package com.kapitannight.kdp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapitannight.kdp2.data.model.Message
import com.kapitannight.kdp2.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // 🔵 StateFlow для чатов
    private val _chats = MutableStateFlow<List<com.kapitannight.kdp2.data.model.Chat>>(emptyList())
    val chats: StateFlow<List<com.kapitannight.kdp2.data.model.Chat>> = _chats.asStateFlow()

    // 🔵 StateFlow для сообщений
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    // 📥 Загрузка чатов
    fun loadMessages(chatId: String) {
        viewModelScope.launch {

            val messagesFromDb = ChatRepository.getMessagesFromDb(chatId)

            _messages.value = messagesFromDb
        }
    }


    fun loadChats() {
        viewModelScope.launch {
            val chatsFromDb = ChatRepository.getChatsFromDb()
            _chats.value = chatsFromDb
        }
    }

    // 📤 Отправка сообщения
    fun sendMessage(content: String, chatId: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            val message = Message(
                chatId = chatId,
                sender = "Вы",
                content = content,
                timestamp = System.currentTimeMillis()
            )
            ChatRepository.addMessageToDb(chatId, message)
            loadMessages(chatId)
            loadChats()

            ChatRepository.addMessageToDb(chatId, message)

            // 🔄 После отправки — обновляем список!
            loadMessages(chatId)
        }
    }
}