package com.suhyeong.yire.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.suhyeong.yire.R
import com.suhyeong.yire.databinding.ActivityApiTestBinding
import com.suhyeong.yire.databinding.ActivitySnsLoginTestBinding
import com.suhyeong.yire.test.binding.Kakao
import com.suhyeong.yire.test.binding.User

class SnsLoginTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySnsLoginTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnsLoginTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this)[Kakao::class.java]
        viewModel.setActivity(this)
        binding.view = viewModel
        binding.lifecycleOwner = this

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
    }
}