package com.kapitannight.kdp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kapitannight.kdp2.data.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getMessages(chatId: String): Flow<List<MessageEntity>>

    @Insert
    suspend fun sendMessage(message: MessageEntity)

    @Update
    fun update(message: MessageEntity)

    @Delete
    fun deleteMessage(message: MessageEntity)
}