package com.android.coworkerforhour.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.coworkerforhour.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user")
    fun getAllLiveData(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE name LIKE :name AND" + " password LIKE :password LIMIT 1")
    fun findByNameAndPassword(name: String, password: String) : Boolean

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteUser(id: Int)
}