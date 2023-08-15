package com.android.coworkerforhour.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.coworkerforhour.activityes.UserProfileActivity
import com.android.coworkerforhour.databinding.FragmentRegisterFormBinding
import com.android.coworkerforhour.interfaces.FragmentNavigation
import com.android.coworkerforhour.objects.FieldValidators.isStringContainNumber
import com.android.coworkerforhour.objects.FieldValidators.isStringContainSpecialCharacter
import com.android.coworkerforhour.objects.FieldValidators.isStringLowerAndUpperCase
import com.android.coworkerforhour.objects.FieldValidators.isValidEmail
import com.google.android.material.textfield.TextInputEditText


class RegisterFormFragment : Fragment() {

    private lateinit var binding: FragmentRegisterFormBinding

    private lateinit var userNameString: String

    private lateinit var emailString: String

    private lateinit var passwordString: String

    inner class TextFieldValidation(view: View, view2: View, view3: View, view4: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.apply {
                when(view){
                    tietEnterUserName -> validateUserName()
                    tietEnterEmail -> validateEmail()
                    tietInputPassword -> validatePassword()
                    tietInputConfPassword -> validateConfPassword()
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterFormBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()

        binding.backToLoginBt.setOnClickListener {
            navigateToFragmentLogIn()
        }

        binding.registerBt.setOnClickListener {
            if (isValidated()){
                Toast.makeText(this.requireContext(),"Validated successfully", Toast.LENGTH_SHORT).show()
                saveProfilePrefs()

            }
        }
    }

    private fun navigateToFragmentLogIn(){
        apply {
            val navToReg = this.activity as FragmentNavigation
            navToReg.navigationFrag(AuthFragment(),false)
        }
    }

    private fun navigateToUserProfile(){
        val userProfileActivityIntent = Intent(requireContext(),UserProfileActivity::class.java)
        startActivity(userProfileActivityIntent)
        requireActivity().finish()
    }


    private fun saveProfilePrefs(){
        binding.apply {

             userNameString = tietEnterUserName.text.toString()
             emailString = tietEnterEmail.text.toString()
             passwordString = tietInputPassword.text.toString()

            val sharedPreferences = activity?.getSharedPreferences(
                AuthFragment.SHARED_PREFS,
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences?.edit()
            with(editor){
                this?.putString("USER_NAME", userNameString)
                this?.putString("EMAIL", emailString)
                this?.putString("USER_PASSWORD", passwordString)
                this?.apply()
            }
        }
        navigateToUserProfile()
    }




    private fun validateUserName() : Boolean {
        binding.apply {
            when{
                tietEnterUserName.text.toString().trim().isEmpty() -> {
                    Toast.makeText(activity,"Require field",Toast.LENGTH_SHORT).show()
                    regFrTILay1.error = "Require Field"
                    tietEnterUserName.requestFocus()
                    return false
                }
                !isStringLowerAndUpperCase(tietEnterUserName.text.toString()) -> {
                    Toast.makeText(
                        activity,
                        "Username must contain at least 1 upper case letter",
                        Toast.LENGTH_SHORT)
                        .show()
                    regFrTILay1.error = "Username must contain at least 1 upper case letter"
                    tietEnterUserName.requestFocus()
                    return false
                }
                regFrTILay1.isErrorEnabled -> false
            }
        }
        return true
    }

    private fun validateEmail() : Boolean {
        binding.apply {
            when{
                tietEnterEmail.text.toString().trim().isEmpty() -> {
                    Toast.makeText(activity,"Require field",Toast.LENGTH_SHORT).show()
                    regFrTILay2.error = "Require Field!"
                    tietEnterEmail.requestFocus()
                    return false
                }
                !isValidEmail(tietEnterEmail.text.toString()) -> {
                    Toast.makeText(activity,"Invalid Email",Toast.LENGTH_SHORT).show()
                    regFrTILay2.error = "Invalid Email"
                    tietEnterEmail.requestFocus()
                    return false
                }
                regFrTILay1.isErrorEnabled -> false
            }
        }
        return true
    }

    private fun validatePassword() : Boolean {
        binding.apply {
            when{
                tietInputPassword.text.toString().trim().isEmpty() -> {
                    Toast.makeText(activity,"Require field",Toast.LENGTH_SHORT).show()
                    regFrTILay3.error = "Require Field!"
                    tietInputPassword.requestFocus()
                    return false
                }
                tietInputPassword.text.toString().length < 6 -> {
                    Toast.makeText(activity,"Password can't be less than 6!",Toast.LENGTH_SHORT).show()
                    regFrTILay3.error = "Password can't be less than 6!"
                    tietInputPassword.requestFocus()
                    return false
                }
                !isStringContainNumber(tietInputPassword.text.toString()) -> {
                    Toast.makeText(activity,"Required at least 1 digit",Toast.LENGTH_SHORT).show()
                    regFrTILay3.error = "Required at least 1 digit"
                    tietInputPassword.requestFocus()
                    return false
                }
                !isStringLowerAndUpperCase(tietInputPassword.text.toString()) -> {
                    Toast.makeText(activity,"Password must contain upper and lower case letters",Toast.LENGTH_SHORT).show()
                    regFrTILay3.error = "Password must contain upper and lower case letters"
                    tietInputPassword.requestFocus()
                    return false
                }
                !isStringContainSpecialCharacter(tietInputPassword.text.toString()) -> {
                    Toast.makeText(activity,"1 special character required",Toast.LENGTH_SHORT).show()
                    regFrTILay3.error = "1 special character required"
                    tietInputPassword.requestFocus()
                    return false
                }
                regFrTILay3.isErrorEnabled -> false
            }
        }
        return true
    }

    private fun validateConfPassword() : Boolean {
        binding.apply {
            when{
                tietInputConfPassword.text.toString().trim().isEmpty() -> {
                    Toast.makeText(activity,"Require field",Toast.LENGTH_SHORT).show()
                    regFrTILay4.error = "Require Field!"
                    tietInputConfPassword.requestFocus()
                    return false
                }
                tietInputConfPassword.text.toString() != tietInputPassword.text.toString() -> {
                    Toast.makeText(activity,"Passwords doesn't match",Toast.LENGTH_SHORT).show()
                    regFrTILay4.error = "Passwords doesn't match"
                    tietInputConfPassword.requestFocus()
                    return false
                }
                regFrTILay4.isErrorEnabled -> false
            }
        }
        return true
    }

    private fun isValidated(): Boolean =
        validateUserName() &&
        validateEmail() &&
        validatePassword() &&
        validateConfPassword()

    private fun setUpListeners(){
        val textChangeListener = TextInputEditText(this.requireContext())
        binding.apply {
            textChangeListener.
            addTextChangedListener(
                TextFieldValidation(
                    tietEnterUserName,
                    tietEnterEmail,
                    tietInputPassword,
                    tietInputConfPassword
                )
            )
        }
    }


    companion object {

        const val MY_PREFS = "sharedPrefs"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFormFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}