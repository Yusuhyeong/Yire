package com.suhyeong.yire.listener

interface LoginCheckListener {
    fun onLoginSuccess(uid: String, nickName: String)
    fun onLoginFailure(error: String)
}