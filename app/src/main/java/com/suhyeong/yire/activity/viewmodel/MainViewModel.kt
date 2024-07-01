package com.suhyeong.yire.activity.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _selectedId = MutableLiveData<String>()
    val selectedId: LiveData<String>
        get() = _selectedId

    fun bottomClick(view: View) {
        _selectedId.value = view.id.toString()
    }
}