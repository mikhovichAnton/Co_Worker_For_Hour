package com.android.coworkerforhour.activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.coworkerforhour.R
import com.android.coworkerforhour.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}