package com.suhyeong.yire.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.suhyeong.yire.R
import com.suhyeong.yire.activity.model.view.Login
import com.suhyeong.yire.activity.model.view.NickName
import com.suhyeong.yire.databinding.ActivityLoginBinding
import com.suhyeong.yire.databinding.ActivityNickNameBinding
import com.suhyeong.yire.fragment.CommonPopUp
import com.suhyeong.yire.listener.LoginCheckListener
import com.suhyeong.yire.listener.PopUpClickListener

class NickNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNickNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNickNameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val uidIntent = intent.getStringExtra("uid")

        val viewModel = ViewModelProvider(this)[NickName::class.java]
        viewModel.setActivity(this, uidIntent.toString())
        binding.view = viewModel
        binding.lifecycleOwner = this
    }
}