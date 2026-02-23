package com.kapitannight.kdp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val lastMessage: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isOnline: Boolean = false,
    val content: String = ""
)