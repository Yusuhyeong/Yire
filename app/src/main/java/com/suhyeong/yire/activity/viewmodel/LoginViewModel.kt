package com.suhyeong.yire.activity.viewmodel

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.suhyeong.yire.activity.NickNameActivity
import com.suhyeong.yire.firebase.Firestore
import com.suhyeong.yire.listener.LoginCheckListener
import com.suhyeong.yire.utils.Constants

class LoginViewModel: ViewModel() {
    private val _try_kakao_login = MutableLiveData<Boolean>()
    val try_kakao_login: LiveData<Boolean> = _try_kakao_login


    fun loginKakao() {
        _try_kakao_login.value = true
    }

    fun setDimVisibility(isVisible: Boolean) {
        _try_kakao_login.value = isVisible
    }
}