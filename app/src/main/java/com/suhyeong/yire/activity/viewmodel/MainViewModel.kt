package com.suhyeong.yire.activity.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhyeong.yire.api.ItsApiClient
import com.suhyeong.yire.listener.OnTextChangedListener

class MainViewModel : ViewModel() {
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery
    private val _result = MutableLiveData<List<com.suhyeong.yire.api.response.SearchResult>>()
    val result: LiveData<List<com.suhyeong.yire.api.response.SearchResult>> = _result
    private val _try_search_music = MutableLiveData<Boolean>()
    val try_search_music: LiveData<Boolean> = _try_search_music

    private val _selectedId = MutableLiveData<String>()
    val selectedId: LiveData<String>
        get() = _selectedId

    fun bottomClick(view: View) {
        _selectedId.value = view.id.toString()
    }

    fun onQueryTextChanged(query: String) {
        _searchQuery.value = query
    }

    fun apiSearch(query: String) {
        _try_search_music.value = true
        ItsApiClient.instance.searchMusic(query) { result, error ->
            result?.let {
                Log.d("결과", result.toString())
                Log.d("결과", "result size : ${result.size}")

                _result.value = result
            }
            error?.let {
                Log.d("결과에러", error.toString())
            }

            _try_search_music.value = false
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onTextChanged")
        fun setOnTextChangedListener(editText: EditText, onTextChanged: OnTextChangedListener) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onTextChanged.onTextChanged(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}