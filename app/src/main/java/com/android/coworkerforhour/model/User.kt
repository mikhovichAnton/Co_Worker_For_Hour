package com.android.coworkerforhour.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "user"
)

data class User(
    val type: Byte,
    val name: String,
    val email: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

){
    companion object{
        const val TYPE_WORKER: Byte = 1
        const val TYPE_EMPLOYER: Byte = 2
    }
}
