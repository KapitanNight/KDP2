package com.kapitannight.kdp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kapitannight.kdp2.data.model.ChatEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface ChatDao {
    @Query("SELECT * FROM chats")
    fun getChats(): Flow<List<ChatEntity>>

    @Query("SELECT * FROM chats WHERE id = :chatId")
    fun getId(chatId: String): Flow<ChatEntity?>

    @Insert
    fun addChat(chat: ChatEntity)

    @Update
    fun updateChat(chat: ChatEntity)

    @Delete
    fun deleteChat(chat: ChatEntity)
}