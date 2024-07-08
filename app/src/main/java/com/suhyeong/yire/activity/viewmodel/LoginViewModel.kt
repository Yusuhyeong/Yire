package com.suhyeong.yire.activity.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _try_kakao_login = MutableLiveData<Boolean>()
    val try_kakao_login: LiveData<Boolean> = _try_kakao_login
    private val _test = MutableLiveData<View>()
    val test: LiveData<View> = _test


    fun loginKakao() {
        _try_kakao_login.value = true
    }

    fun setDimVisibility(isVisible: Boolean) {
        _try_kakao_login.value = isVisible
    }
}