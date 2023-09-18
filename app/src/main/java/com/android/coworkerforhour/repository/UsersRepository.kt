package com.android.coworkerforhour.repository

import androidx.lifecycle.LiveData
import com.android.coworkerforhour.database.dao.UsersDao
import com.android.coworkerforhour.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class UsersRepository(val usersDao: UsersDao) {
    private val job = SupervisorJob()
    private val usersScope = CoroutineScope(job + Dispatchers.IO)

    suspend fun insertUser(user: User){
        usersScope.launch {
            usersDao.insert(user)
        }
    }

    suspend fun getUserByNameAndPassword(name: String, password:String) : Boolean {
        return usersScope.async {
            usersDao.findByNameAndPassword(name,password)
        }.await()
    }

    fun getAllUsers() = usersDao.getAllUsers()


    fun getAllUsersLiveData() = usersDao.getAllLiveData()


}