package com.kapitannight.kdp2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kapitannight.kdp2.data.dao.ChatDao
import com.kapitannight.kdp2.data.dao.MessageDao
import com.kapitannight.kdp2.data.model.ChatEntity
import com.kapitannight.kdp2.data.model.MessageEntity

@Database(
    entities = [
        ChatEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = false

)
abstract class AppDatabase: RoomDatabase() {

    abstract fun chatDao(): ChatDao

    abstract fun messageDao(): MessageDao
}