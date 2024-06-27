/**
 * 2024.06.27
 * Intro 화면 (접속 시 네트워크 연결 상태 확인 -> 연결 확인 후, 2초 뒤 LoginActivity로 이동)
 *
 * Todo
 * 1. IntroActivity 로딩 텍스트 -> 로딩 이미지
 */

package com.suhyeong.yire.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.suhyeong.yire.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startTimer(isNetworkConnected())
    }

    private fun startTimer (isNetwork: Boolean) {
        binding.tvNetworkCheck.setText("네트워크 확인중")
        if (isNetwork)  {
            binding.tvNetworkCheck.setText("네트워크 연결 확인")
            Handler(Looper.getMainLooper()).postDelayed({
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }, 2000)
        } else {
            binding.tvNetworkCheck.setText("네트워크 연결 실패")
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}