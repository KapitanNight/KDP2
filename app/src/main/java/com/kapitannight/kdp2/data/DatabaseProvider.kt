package com.kapitannight.kdp2.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase{
        if(database == null){
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "kdp2_messenger_db"
            ).allowMainThreadQueries().build()

        }
        return database!!
    }
}