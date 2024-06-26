package com.suhyeong.yire.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.suhyeong.yire.R
import com.suhyeong.yire.databinding.ActivityBindingBinding
import com.suhyeong.yire.test.binding.User

class BindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewModel = ViewModelProvider(this)[User::class.java]
        binding.lifecycleOwner = this
    }
}