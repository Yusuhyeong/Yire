package com.suhyeong.yire.test

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.suhyeong.yire.R
import com.suhyeong.yire.api.ItsApiClient

class ApiTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_api_test)

        ItsApiClient.instance.searchMusic("아야츠노 유니") { result, error ->
            result?.let { Log.d("결과", result.toString()) }
            error?.let { Log.d("결과에러", error.toString()) }
        }
    }
}