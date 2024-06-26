package com.suhyeong.yire.test.binding

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class Kakao: ViewModel() {
    private lateinit var _activity: AppCompatActivity
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun setActivity(activity: AppCompatActivity) {
        _activity = activity
    }

    fun statusUpdate() {
        if (loginCheck())
            _status.value = "로그인 성공"
        else
            _status.value = "로그인 실패"
    }

    fun loginCheck() : Boolean {
        KakaoSdk.init(_activity.applicationContext, "0690af0015f912d642074fbbf0afcf7f")

        var checkValue:Boolean = false
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(_activity.applicationContext)) {
            // 가능하다면 카카오톡으로 로그인하기
            UserApiClient.instance.loginWithKakaoTalk(_activity.applicationContext) { token, error ->
                if (error != null) {
                    Log.d("loginCheck", "카카오톡으로 로그인 실패! " + error.message)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                } else if (token != null) {
                    Log.d("loginCheck", "카카오톡으로 로그인 성공! " + token.accessToken)
                    checkValue = true
                }

                // kakao로 로그인 하지 못 할 경우 계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(
                    _activity.applicationContext,
                    callback = callback
                )
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                _activity.applicationContext,
                callback = callback
            )
        }

        return checkValue
    }

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("callback", "카카오 계정으로 로그인 실패! " + error.message)
            _status.value = "로그인 실패"
        } else if (token != null) {
            Log.d("callback", "카카오 계정으로 로그인 성공! " + token.accessToken)
            _status.value = "로그인 성공"
        }
    }
}