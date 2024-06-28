package com.suhyeong.yire.listener

interface LoginCheckListener {
    fun onLoginSuccess(uid: String)
    fun onLoginFailure(error: String)
}