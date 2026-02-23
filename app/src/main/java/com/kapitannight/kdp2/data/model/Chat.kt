package com.kapitannight.kdp2.data.model

data class Chat(
        val id: String,
        val name: String = "Default",
        val lastMessage: String = "",
        val timestamp: Long = System.currentTimeMillis(),
        val isOnline: Boolean = false,
        val content: String = ""
)