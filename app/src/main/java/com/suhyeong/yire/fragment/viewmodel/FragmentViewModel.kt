package com.suhyeong.yire.fragment.viewmodel

import android.app.appsearch.SearchResult
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhyeong.yire.api.ItsApiClient
import com.suhyeong.yire.listener.OnTextChangedListener

class FragmentViewModel: ViewModel() {
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery
    private val _result = MutableLiveData<List<com.suhyeong.yire.api.response.SearchResult>>()
    val result: LiveData<List<com.suhyeong.yire.api.response.SearchResult>> = _result

    fun onQueryTextChanged(query: String) {
        _searchQuery.value = query
    }

    fun apiSearch(query: String) {
        ItsApiClient.instance.searchMusic(query) { result, error ->
            result?.let {
                Log.d("결과", result.toString())
                Log.d("결과", "result size : ${result.size}")

                _result.value = result
            }
            error?.let {
                Log.d("결과에러", error.toString())
            }
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