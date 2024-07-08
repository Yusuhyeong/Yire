package com.suhyeong.yire.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.suhyeong.yire.activity.viewmodel.LoginViewModel
import com.suhyeong.yire.databinding.ActivityLoginBinding
import com.suhyeong.yire.firebase.Firestore
import com.suhyeong.yire.test.MainTestActivity
import com.suhyeong.yire.utils.Constants

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val firestore = Firestore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this

        val keyHash = Utility.getKeyHash(this)
        Log.d("YLOG", keyHash)

        binding.tvLoginTitle.setOnClickListener {
            val intent = Intent(this, MainTestActivity::class.java)
            startActivity(intent)
            finish()
        }

        KakaoSdk.init(this.applicationContext, "0690af0015f912d642074fbbf0afcf7f")

        viewModel.try_kakao_login.observe(this) {
            if (viewModel.try_kakao_login.value == true) {
                loginCheck()
                binding.clLoading.isVisible = true
            } else {
                viewModel.setDimVisibility(false)
            }
        }
    }

    fun onLoginSuccess(uid: String, nickName: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("uid", uid)
        intent.putExtra("nickName", nickName)
        startActivity(intent)
        finish()
    }

    fun onLoginFailure(error: String) {
        binding.loginViewModel?.setDimVisibility(false)
        if (Constants.UNREGISTERED_USER.equals(error)) {
            Log.d("YLOG", "등록되지 않은 유저")
        } else if (Constants.KAKAO_LOGIN_ERROR.equals(error)) {
            Log.d("YLOG", "카카오 로그인 실패")
        }
    }

    fun loginCheck() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this.applicationContext)) {
            UserApiClient.instance.loginWithKakaoTalk(this.applicationContext) { token, error ->
                if (error != null) {
                    Log.d("YLOG", "카카오톡으로 로그인 실패! " + error.message)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(
                        this,
                        callback = callback
                    )

                } else if (token != null) {
                    Log.d("YLOG", "카카오톡으로 로그인 성공! " + token.accessToken)
                    fetchUserInfo()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                this.applicationContext,
                callback = callback
            )
        }
    }
    private fun fetchUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("YLOG", "사용자 정보 요청 실패: ${error.message}")
            } else if (user != null) {
                Log.d("YLOG", "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.nickname}")
                Log.d("YLOG", "사용자 ID: ${user.id}")

                val userId = user.id.toString()
                Log.d("YLOG", "uid : ${userId}")
                firestore.checkUidAndNickname(userId) { uidExists, nicknameExists, nickname ->
                    if (uidExists) {
                        Log.d("YLOG", "uid 있음")
                        if (nicknameExists) {
                            Log.d("YLOG", "nickname 있음 ${nickname}")
                            onLoginSuccess(userId, nickname.toString())
                        } else {
                            Log.d("YLOG", "nickname 없음")
                            val intent = Intent(this, NickNameActivity::class.java)
                            intent.putExtra("uid", userId)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.d("YLOG", "uid 없음")
                        val intent = Intent(this, NickNameActivity::class.java)
                        intent.putExtra("uid", userId)
                        startActivity(intent)
                        firestore.setUid(userId) { success ->
                            if (success) {
                                val intent = Intent(this, NickNameActivity::class.java)
                                intent.putExtra("uid", userId)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.e("YLOG", "UID 저장 실패")
                            }
                        }
                    }
                }
            }
        }
    }

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("YLOG", "카카오 계정으로 로그인 실패! " + error.message)
            Log.d("YLOG", "로그인 실패")
            onLoginFailure(Constants.KAKAO_LOGIN_ERROR)
        } else if (token != null) {
            Log.d("YLOG", "카카오 계정으로 로그인 성공! " + token.accessToken)
            Log.d("YLOG", "로그인 성공")
            fetchUserInfo()
        }
    }
}