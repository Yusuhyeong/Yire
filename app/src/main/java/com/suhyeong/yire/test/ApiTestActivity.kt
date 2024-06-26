package com.suhyeong.yire.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suhyeong.yire.R
import com.suhyeong.yire.api.ItsApiClient
import com.suhyeong.yire.databinding.ActivityApiTestBinding
import com.suhyeong.yire.databinding.ActivitySnsLoginTestBinding
import com.suhyeong.yire.test.binding.Kakao
import com.suhyeong.yire.test.binding.MusicSearch

class ApiTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiTestBinding
    private lateinit var viewModel: MusicSearch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_test)

        binding = ActivityApiTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MusicSearch::class.java]
        binding.musicSearchModel = viewModel
        binding.lifecycleOwner = this
    }
}