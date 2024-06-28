package com.suhyeong.yire.activity.model.view

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhyeong.yire.activity.MainActivity
import com.suhyeong.yire.fragment.CommonPopUp
import com.suhyeong.yire.listener.PopUpClickListener
import com.suhyeong.yire.test.listener.OnTextChangedListener

class NickName: ViewModel() {
    private lateinit var _activity: AppCompatActivity
    private lateinit var uid: String
    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    fun setActivity(activity: AppCompatActivity, uid: String) {
        _activity = activity
        this.uid = uid
    }

    fun onQueryTextChanged(nickName: String) {
        _nickName.value = nickName
    }

    fun setNickName(nickName: String) {
        showCommonDialog("닉네임 확인", "${nickName}이(가) 맞나요?\n추후 변경 가능합니다.", false, nickName)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onTextChanged")
        fun setOnTextChangedListener(editText: EditText, onTextChanged: OnTextChangedListener) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onTextChanged.onTextChanged(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun showCommonDialog(title: String, detail: String, isTwo: Boolean, nickName: String) {
        val dialog = CommonPopUp.newInstance(title, detail, isTwo)

        dialog.setPopUpListener(object : PopUpClickListener {
            override fun onConfirm() {
                // 확인 버튼 클릭 처리
                Log.d("CommonDialog", "확인 버튼 클릭")
                val intent = Intent(_activity, MainActivity::class.java)
                intent.putExtra("uid", uid)
                intent.putExtra("nickName", nickName)
                _activity.startActivity(intent)
                _activity.finish()
                _activity.finish()
            }

            override fun onCancel() {
                // 취소 버튼 클릭 처리
                Log.d("CommonDialog", "취소 버튼 클릭")
            }
        })

        dialog.show(_activity.supportFragmentManager, "CommonDialog")
    }
}