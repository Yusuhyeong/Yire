package com.suhyeong.yire.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.suhyeong.yire.activity.viewmodel.NickNameViewModel
import com.suhyeong.yire.databinding.ActivityNickNameBinding
import com.suhyeong.yire.firebase.Firestore
import com.suhyeong.yire.fragment.CommonPopUp
import com.suhyeong.yire.listener.PopUpClickListener

class NickNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNickNameBinding
    private lateinit var uid: String
    private val firestore = Firestore()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNickNameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        uid = intent.getStringExtra("uid").toString()

        val viewModel = ViewModelProvider(this)[NickNameViewModel::class.java]
        viewModel.setActivity(uid)
        binding.view = viewModel
        binding.lifecycleOwner = this

        viewModel.show_popup_event.observe(this) { msg ->
            if (!viewModel.nickName.value.isNullOrEmpty()) {
                showCommonDialog("닉네임 확인", msg, viewModel.nickName.value.toString(), true)
            } else {
                showCommonDialog("닉네임 확인", msg, "", false)
            }
        }
    }

    private fun showCommonDialog(title: String, detail: String, nickName: String, isTwo: Boolean) {
        val dialog = CommonPopUp.newInstance(title, detail, isTwo)

        dialog.setPopUpListener(object : PopUpClickListener {
            override fun onConfirm() {
                // 확인 버튼 클릭 처리
                Log.d("CommonDialog", "확인 버튼 클릭 ${nickName}")
                if (!nickName.isNullOrEmpty()) {
                    val intent = Intent(this@NickNameActivity, MainActivity::class.java)
                    intent.putExtra("uid", uid)
                    intent.putExtra("nickName", nickName)

                    firestore.setNickName(uid, nickName) { setSuccess ->
                        if (setSuccess) {
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }

            override fun onCancel() {
                // 취소 버튼 클릭 처리
                Log.d("CommonDialog", "취소 버튼 클릭")
            }
        })

        dialog.show(this.supportFragmentManager, "CommonDialog")
    }
}