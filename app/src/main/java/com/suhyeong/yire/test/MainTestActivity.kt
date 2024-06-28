package com.suhyeong.yire.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.suhyeong.yire.databinding.ActivityMainTestBinding
import com.suhyeong.yire.fragment.CommonPopUp
import com.suhyeong.yire.listener.PopUpClickListener

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

        binding.tvPopupTest.setOnClickListener {
            showCommonDialog("테스트", "긴 문장에 대해 테스트\n잘 나오는지 확인해 보자. 길게길게 쓰자.", false)
        }
    }

    private fun showCommonDialog(title: String, detail: String, isTwo: Boolean) {
        val dialog = CommonPopUp.newInstance(title, detail, isTwo)

        dialog.setPopUpListener(object : PopUpClickListener {
            override fun onConfirm() {
                // 확인 버튼 클릭 처리
                Log.d("CommonDialog", "확인 버튼 클릭")
            }

            override fun onCancel() {
                // 취소 버튼 클릭 처리
                Log.d("CommonDialog", "취소 버튼 클릭")
            }
        })

        dialog.show(supportFragmentManager, "CommonDialog")
    }
}