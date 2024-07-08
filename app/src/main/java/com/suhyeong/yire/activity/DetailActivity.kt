package com.suhyeong.yire.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.suhyeong.yire.R
import com.suhyeong.yire.activity.viewmodel.DetailViewModel
import com.suhyeong.yire.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var artistName: String
    private lateinit var artworkUrl100: String
    private lateinit var trackCensoredName: String
    private lateinit var trackTimeMillis: String
    private lateinit var previewUrl: String
    private lateinit var trackName: String
    private lateinit var releaseDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding.detailViewModel = viewModel
        binding.lifecycleOwner = this

        artistName = intent.getStringExtra("artistName").toString()
        artworkUrl100 = intent.getStringExtra("artworkUrl100").toString()
        trackCensoredName = intent.getStringExtra("trackCensoredName").toString()
        trackTimeMillis = intent.getStringExtra("trackTimeMillis").toString()
        previewUrl = intent.getStringExtra("previewUrl").toString()
        trackName = intent.getStringExtra("trackName").toString()
        releaseDate = intent.getStringExtra("releaseDate").toString()

        binding.tvSinger.text = artistName
        binding.tvTitle.text = trackCensoredName
        Glide.with(this).load(artworkUrl100).into(binding.imgAlbumCover)
        binding.tvDetail.text = "수록 앨범 : ${trackName} | 발매일 : ${releaseDate} | 재생시간 : ${trackTimeMillis}"

        viewModel.setDetailString(previewUrl)

        viewModel.stats_play.observe(this, Observer { status ->
            if (status) {
                binding.imgPlay.setBackgroundResource(R.drawable.icon_stop)
            } else {
                binding.imgPlay.setBackgroundResource(R.drawable.icon_play)
            }
        })

        binding.imgBack.setOnClickListener {
            viewModel.stopAudio()
            finish()
        }
    }

    override fun onStop() {
        viewModel.stopAudio()
        super.onStop()
    }

    override fun finish() {
        viewModel.stopAudio()
        super.finish()
    }
}