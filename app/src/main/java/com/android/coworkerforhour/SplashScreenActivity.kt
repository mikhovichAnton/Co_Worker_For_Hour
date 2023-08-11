package com.android.coworkerforhour

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.coworkerforhour.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }
}