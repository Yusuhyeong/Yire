package com.suhyeong.yire.test.binding

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

class Kakao: ViewModel() {
    private lateinit var _activity: AppCompatActivity
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun setActivity(activity: AppCompatActivity) {
        _loading.value = false
        _activity = activity
    }

    fun statusUpdate() {
        _loading.value = true
        if (loginCheck())
            _status.value = "로그인 성공"
        else
            _status.value = "로그인 실패"
    }

    fun loginCheck() : Boolean {
        KakaoSdk.init(_activity.applicationContext, "0690af0015f912d642074fbbf0afcf7f")

        var checkValue:Boolean = false
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(_activity.applicationContext)) {
            UserApiClient.instance.loginWithKakaoTalk(_activity.applicationContext) { token, error ->
                if (error != null) {
                    Log.d("loginCheck", "카카오톡으로 로그인 실패! " + error.message)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(
                        _activity,
                        callback = callback
                    )

                } else if (token != null) {
                    Log.d("loginCheck", "카카오톡으로 로그인 성공! " + token.accessToken)
                    checkValue = true
                    _loading.value = false
                    fetchUserInfo()
                }
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
            _loading.value = false
        } else if (token != null) {
            Log.d("callback", "카카오 계정으로 로그인 성공! " + token.accessToken)
            _status.value = "로그인 성공"
            _loading.value = false
            fetchUserInfo()
        }
    }

    private fun fetchUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("fetchUserInfo", "사용자 정보 요청 실패: ${error.message}")
            } else if (user != null) {
                Log.d("fetchUserInfo", "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.nickname}")
                Log.d("fetchUserInfo", "사용자 ID: ${user.id}")

                _status.value = "로그인 성공: ${user.kakaoAccount?.profile?.nickname} (${user.id})"
                // -> 사용자 ID와 firebase의 uid와 비교한 후 값이 있다면 홈 화면으로 이동
                // -> 없다면 팝업 (회원가입 요청)
            }
        }
    }
}