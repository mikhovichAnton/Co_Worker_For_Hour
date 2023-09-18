package com.android.coworkerforhour.activityes.viewModel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.coworkerforhour.model.User
import com.android.coworkerforhour.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UsersRepository): ViewModel() {

    val users: LiveData<List<User>> = repository.getAllUsersLiveData()

    val user: Flow<List<User>> = repository.getAllUsers()



    fun insertUser(userName: String, userEmail: String, usersPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(User(
                name = userName,
                email = userEmail,
                password = usersPassword))
        }
    }
}

class UsersViewModelFactory(private val repository: UsersRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewModel!")
    }
}