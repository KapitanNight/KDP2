package com.kapitannight.kdp2.data.model

import java.util.UUID

data class Message(
        val id: String = UUID.randomUUID().toString(),
        val chatId: String = "",
        val sender: String = "Default",
        val content: String = "",
        val timestamp: Long = System.currentTimeMillis()
)
