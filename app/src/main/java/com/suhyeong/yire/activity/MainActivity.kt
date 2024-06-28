package com.suhyeong.yire.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suhyeong.yire.R
import com.suhyeong.yire.databinding.ActivityMainBinding
import com.suhyeong.yire.databinding.ActivityMainTestBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val uidIntent = intent.getStringExtra("uid")
        val nickNameIntent = intent.getStringExtra("nickName")

        binding.tvTest.text = uidIntent
        binding.tvTest2.text = nickNameIntent
    }
}