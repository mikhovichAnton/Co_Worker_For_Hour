package com.android.coworkerforhour.activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.coworkerforhour.R
import com.android.coworkerforhour.databinding.ActivityUserProfileBinding
import com.android.coworkerforhour.fragments.UserProfileFragment
import com.android.coworkerforhour.interfaces.FragmentNavigation

class UserProfileActivity : AppCompatActivity(), FragmentNavigation {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startUserProfileFragment()
    }

    private fun startUserProfileFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.user_profile_container, UserProfileFragment())
            .commit()
    }

    override fun navigationFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.user_profile_container,fragment)

        if (addToStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    companion object{

    }
}