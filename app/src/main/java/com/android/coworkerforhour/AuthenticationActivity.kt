package com.android.coworkerforhour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.coworkerforhour.databinding.ActivityAuthentificationBinding
import com.android.coworkerforhour.fragments.AuthFragment
import com.android.coworkerforhour.interfaces.FragmentNavigation


class AuthenticationActivity : AppCompatActivity(), FragmentNavigation{

    lateinit var binding: ActivityAuthentificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.auth_container,AuthFragment())
            .commit()


    }

    override fun navigationFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.auth_container,fragment)

        if (addToStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    companion object{

    }
}