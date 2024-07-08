package com.suhyeong.yire.activity.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhyeong.yire.listener.OnTextChangedListener

class NickNameViewModel: ViewModel() {
    private lateinit var uid: String
    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName
    private val _show_popup_event = MutableLiveData<String>()
    val show_popup_event: LiveData<String> = _show_popup_event

    fun setActivity(uid: String) {
        this.uid = uid
    }

    fun onQueryTextChanged(nickName: String) {
        _nickName.value = nickName
    }

    fun showPopup(msg: String) {
        _show_popup_event.value = msg
    }

    fun saveNickName() {
        val nickName = nickName.value
        if (nickName.isNullOrEmpty()) {
            showPopup("닉네임이 입력되지 않았어요!")
        } else {
            showPopup("${nickName}이(가) 맞나요?\n추후 변경 가능합니다.")
        }
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
}