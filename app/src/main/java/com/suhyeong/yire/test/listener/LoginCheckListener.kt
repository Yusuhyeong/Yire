package com.suhyeong.yire.test.listener

interface LoginCheckListener {
    fun onLoginSuccess(uid: String)
    fun onLoginFailure(error: String)
}