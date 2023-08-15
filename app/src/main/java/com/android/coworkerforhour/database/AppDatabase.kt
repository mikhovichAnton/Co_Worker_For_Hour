package com.android.coworkerforhour.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.coworkerforhour.database.dao.UsersDao
import com.android.coworkerforhour.model.User


@Database(
    entities = [User::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object{
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "co_users"

        private var INSTANCE:AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}