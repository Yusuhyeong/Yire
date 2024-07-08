package com.suhyeong.yire.activity.viewmodel

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.IOException

class DetailViewModel: ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private val _stats_play = MutableLiveData<Boolean>()
    val stats_play: LiveData<Boolean> = _stats_play
    private val _detailString = MutableLiveData<String>()
    val detailString: LiveData<String> = _detailString

    fun setDetailString(value: String) {
        _detailString.value = value
        _stats_play.value = false
    }

    fun playAudio(url: String) {
        if (_stats_play.value == true) {
            Log.d("YLOG", "status : true")
            _stats_play.value = false
            stopAudio()
            return
        }

        if (mediaPlayer == null) {
            Log.d("YLOG", "status : false")
            _stats_play.value = true
            mediaPlayer = MediaPlayer().apply {
                setOnPreparedListener {
                    it.start()
                }
                setOnCompletionListener {
                    stopAudio()
                }
                setOnErrorListener { _, _, _ ->
                    true
                }
            }
            try {
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.prepareAsync()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            mediaPlayer?.start()
        }
    }

    fun stopAudio() {
        _stats_play.value = false
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}