package com.android.coworkerforhour.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.coworkerforhour.activityes.AuthenticationActivity
import com.android.coworkerforhour.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    private lateinit var userName : String
    private lateinit var userEmail : String
    private lateinit var userPassword : String

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

        getSharedPreFromRegist()

        binding.apply {
            usernameProfFr.text = userName
            userEmailProfFr.text = userEmail
            userPasswordProfFr.text = userPassword
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