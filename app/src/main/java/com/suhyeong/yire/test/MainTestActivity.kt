package com.suhyeong.yire.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.suhyeong.yire.api.ItsApiClient
import com.suhyeong.yire.databinding.ActivityMainTestBinding

class MainTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Api Test (가수 검색)
        binding.tvApiTest.setOnClickListener {
            val intent = Intent(this, ApiTestActivity::class.java)
            startActivity(intent)
        }

        // SNS 로그인 (카카오톡)
        binding.tvLoginTest.setOnClickListener {
            val intent = Intent(this, SnsLoginTestActivity::class.java)
            startActivity(intent)
        }

        binding.tvBindingTest.setOnClickListener {
            val intent = Intent(this, BindingActivity::class.java)
            startActivity(intent)
        }
    }
}