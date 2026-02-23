package com.kapitannight.kdp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "messages")
data class MessageEntity (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val chatId: String,
    val sender: String = "Default",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)