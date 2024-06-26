package com.suhyeong.yire.test.binding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class User: ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone
    private val _company = MutableLiveData<String>()
    val company: LiveData<String> = _company

    fun update() {
        _name.value = "유수형"
        _phone.value = "010-4993-6984"
        _company.value = "DKI 테크놀로지"
    }
}