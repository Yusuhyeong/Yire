package com.suhyeong.yire.test.binding

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

class MusicSearch: ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    fun onQueryTextChanged(query: String) {
        _searchQuery.value = query
    }

    fun apiSearch(query: String) {
        _status.value = "검색 중"
        ItsApiClient.instance.searchMusic(query) { result, error ->
            result?.let {
                Log.d("결과", result.toString())
                _status.value = "검색 성공"

                Log.d("결과", "result size : ${result.size}")

                for (i: Int in 0 until result.size) {
                    Log.d("결과", "==============================================")
                    Log.d("결과", "가수 : " + result.get(i).artistName.toString())
                    Log.d("결과", "해당 앨범 : " + result.get(i).trackName.toString())
                    Log.d("결과", "앨범 커버 : " + result.get(i).artworkUrl100.toString())
                    Log.d("결과", "출시일 : " + result.get(i).releaseDate.toString())
                    Log.d("결과", "장르 : " + result.get(i).primaryGenreName.toString())
                    Log.d("결과", "==============================================")
                }
            }
            error?.let {
                Log.d("결과에러", error.toString())
                _status.value = "검색 실패"
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