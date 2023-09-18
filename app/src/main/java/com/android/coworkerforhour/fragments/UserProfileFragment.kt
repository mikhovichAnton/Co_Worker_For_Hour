package com.android.coworkerforhour.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.android.coworkerforhour.UsersApp
import com.android.coworkerforhour.activityes.AuthenticationActivity
import com.android.coworkerforhour.activityes.viewModel.UsersViewModel
import com.android.coworkerforhour.activityes.viewModel.UsersViewModelFactory
import com.android.coworkerforhour.databinding.FragmentUserProfileBinding
import com.android.coworkerforhour.repository.UsersRepository


class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    private lateinit var userName : String
    private lateinit var userEmail : String
    private lateinit var userPassword : String
    private lateinit var repository: UsersRepository
    private lateinit var viewModel: UsersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UsersRepository(UsersApp.INSTANCE.database.usersDao())
        viewModel = ViewModelProvider(this,UsersViewModelFactory(repository))
            .get(UsersViewModel::class.java)

        getSharedPreFromRegist()




        binding.apply {
            usernameProfFr.text = "Welcome user ${userName}"
        }

        binding.logOutBt.setOnClickListener {
            logOut()
        }
    }


    private fun getSharedPreFromRegist() {
        val sharedPreferences = activity?.getSharedPreferences(MY_PREFS, MODE_PRIVATE)
        with(sharedPreferences){
             userName = this?.getString("USER_NAME","").toString()
             userEmail = this?.getString("EMAIL","").toString()
             userPassword = this?.getString("USER_PASSWORD","").toString()
        }
    }

    private fun logOut(){
        removeData()
        val authenticationActivity = Intent(requireContext(),AuthenticationActivity::class.java)
        startActivity(authenticationActivity)
        requireActivity().finish()
    }

    private fun removeData(){
        val sharedPreferences = activity?.getSharedPreferences(AuthFragment.SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }

    companion object {

        const val MY_PREFS = "sharedPrefs"
        const val USER_NAME = "userName"
        const val USER_EMAIL = "userEmail"
        const val USER_PASSWORD = "userPassword"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}