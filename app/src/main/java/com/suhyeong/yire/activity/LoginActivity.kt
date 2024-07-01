package com.suhyeong.yire.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.common.util.Utility
import com.suhyeong.yire.activity.viewmodel.LoginViewModel
import com.suhyeong.yire.databinding.ActivityLoginBinding
import com.suhyeong.yire.listener.LoginCheckListener
import com.suhyeong.yire.test.MainTestActivity
import com.suhyeong.yire.utils.Constants

class LoginActivity : AppCompatActivity(), LoginCheckListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.setActivity(this, this)
        binding.view = viewModel
        binding.lifecycleOwner = this

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        binding.tvLoginTitle.setOnClickListener {
            val intent = Intent(this, MainTestActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onLoginSuccess(uid: String, nickName: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("uid", uid)
        intent.putExtra("nickName", nickName)
        startActivity(intent)
        finish()
    }

    override fun onLoginFailure(error: String) {
        if (Constants.UNREGISTERED_USER.equals(error))
            Log.d("LoginActivity", "등록되지 않은 유저")
        else if (Constants.KAKAO_LOGIN_ERROR.equals(error))
            Log.d("LoginActivity", "카카오 로그인 실패")
    }
}