package com.android.coworkerforhour.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import com.android.coworkerforhour.R
import com.android.coworkerforhour.activityes.UserProfileActivity
import com.android.coworkerforhour.databinding.FragmentAuthBinding
import com.android.coworkerforhour.interfaces.FragmentNavigation
import com.android.coworkerforhour.objects.FieldValidators
import com.google.android.material.textfield.TextInputEditText
import kotlin.properties.Delegates


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding


    inner class TextFieldValidation(view: View, view2: View) :
        TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.apply {
                when(view){
                    userNameLoginFr -> validateUserName()
                    passwordFrLogin -> validatePassword()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        putPrefsToConsts()

        checkPrefsOnStart()

        setUpListeners()

        binding.logInBt.setOnClickListener {
            if (isValidated()) {
                putPrefsToConsts()
                checkPrefsOnClick()
            }
        }

        binding.signInBt.setOnClickListener {
            navigateToFragmentRegistration()
        }
    }

    private fun putPrefsToConsts() {
        val sharedPreferences = activity?.getSharedPreferences(
            UserProfileFragment.MY_PREFS,
            Context.MODE_PRIVATE
        )
        USER_NAME = sharedPreferences?.getString("USER_NAME", "").toString()
       USER_PASSWORD = sharedPreferences?.getString("USER_PASSWORD","").toString()

    }

    private fun navigateToFragmentRegistration(){
        apply {
            val navToReg = this.activity as FragmentNavigation
            navToReg.navigationFrag(RegisterFormFragment(),false)
        }
    }

    private fun navigateToUserProfile(){
        val userProfileActivityIntent = Intent(requireContext(),UserProfileActivity::class.java)
        startActivity(userProfileActivityIntent)
        requireActivity().finish()
    }
    private fun checkPrefsOnClick(){
        binding.apply {
            val sharedPreferences = activity?.getSharedPreferences(
                AuthFragment.SHARED_PREFS,
                Context.MODE_PRIVATE)

            val userName = sharedPreferences?.getString("USER_NAME", "")
            val password = sharedPreferences?.getString("USER_PASSWORD","")
            val userNameString = userNameLoginFr.text.toString()
            val passwordString = passwordFrLogin.text.toString()

            if (userName.equals(userNameString)&&password.equals(passwordString)){
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
               navigateToUserProfile()
                } else {
                    Toast.makeText(activity,"Check correctness of your inputs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPrefsOnStart(){
        binding.apply {
            val userNameString = USER_NAME
            val passwordString = USER_PASSWORD

            val sharedPreferences = activity?.getSharedPreferences(
                AuthFragment.SHARED_PREFS,
                Context.MODE_PRIVATE)

            val userName = sharedPreferences?.getString("USER_NAME", "")
            val password = sharedPreferences?.getString("USER_PASSWORD","")

            if (userName == "" && password == ""){
                return
            } else if(userName.equals(userNameString)&&password.equals(passwordString)){
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                navigateToUserProfile()
            }
        }
    }

    private fun validateUserName() : Boolean {
        binding.apply {
            when{
                userNameLoginFr.text.toString().trim().isEmpty() -> {
                    Toast.makeText(activity,"Require field",Toast.LENGTH_SHORT).show()
                    authFrTILay.error = "Require Field"
                    userNameLoginFr.requestFocus()
                    return false
                }
                !FieldValidators.isStringLowerAndUpperCase(userNameLoginFr.text.toString()) -> {
                    Toast.makeText(
                        activity,
                        "Username must contain at least 1 upper case letter",
                        Toast.LENGTH_SHORT)
                        .show()
                    authFrTILay.error = "Username must contain at least 1 upper case letter"
                    userNameLoginFr.requestFocus()
                    return false
                }
                authFrTILay.isErrorEnabled -> false
            }
        }
        return true
    }

    private fun validatePassword() : Boolean {
        binding.apply {
            when{
                passwordFrLogin.text.toString().trim().isEmpty() -> {
                    Toast.makeText(activity,"Require field", Toast.LENGTH_SHORT).show()
                    authFrTILay2.error = "Require Field!"
                    passwordFrLogin.requestFocus()
                    return false
                }
                passwordFrLogin.text.toString().length < 6 -> {
                    Toast.makeText(activity,"Password can't be less than 6!", Toast.LENGTH_SHORT).show()
                    authFrTILay2.error = "Password can't be less than 6!"
                    passwordFrLogin.requestFocus()
                    return false
                }
                !FieldValidators.isStringContainNumber(passwordFrLogin.text.toString()) -> {
                    Toast.makeText(activity,"Required at least 1 digit", Toast.LENGTH_SHORT).show()
                    authFrTILay2.error = "Required at least 1 digit"
                    passwordFrLogin.requestFocus()
                    return false
                }
                !FieldValidators.isStringLowerAndUpperCase(passwordFrLogin.text.toString()) -> {
                    Toast.makeText(activity,"Password must contain upper and lower case letters",
                        Toast.LENGTH_SHORT).show()
                    authFrTILay2.error = "Password must contain upper and lower case letters"
                    passwordFrLogin.requestFocus()
                    return false
                }
                !FieldValidators.isStringContainSpecialCharacter(passwordFrLogin.text.toString()) -> {
                    Toast.makeText(activity,"1 special character required", Toast.LENGTH_SHORT).show()
                    authFrTILay2.error = "1 special character required"
                    passwordFrLogin.requestFocus()
                    return false
                }
                authFrTILay2.isErrorEnabled -> false
            }
        }
        return true
    }

    private fun isValidated(): Boolean =
        validateUserName() && validatePassword()

    private fun setUpListeners(){
        val textChangeListener = TextInputEditText(this.requireContext())
        binding.apply {
            textChangeListener.
            addTextChangedListener(
                TextFieldValidation(
                    userNameLoginFr,
                    passwordFrLogin
                )
            )
        }
    }


    companion object {

        const val SHARED_PREFS = "sharedPrefs"
        var USER_NAME = ""
        var USER_PASSWORD = ""
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


}