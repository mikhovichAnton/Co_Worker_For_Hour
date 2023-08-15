package com.android.coworkerforhour

import android.app.Application
import com.android.coworkerforhour.database.AppDatabase

class UsersApp: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: UsersApp
    }
}