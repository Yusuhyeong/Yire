package com.suhyeong.yire.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.suhyeong.yire.R
import com.suhyeong.yire.activity.viewmodel.MainViewModel
import com.suhyeong.yire.activity.viewmodel.MainViewModelFactory
import com.suhyeong.yire.databinding.ActivityMainBinding
import com.suhyeong.yire.fragment.ChattingFragment
import com.suhyeong.yire.fragment.HomeFragment
import com.suhyeong.yire.fragment.MatchingFragment
import com.suhyeong.yire.fragment.MyPlaylistFragment
import com.suhyeong.yire.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        // 홈 화면 초기 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fl_main, HomeFragment())
                .addToBackStack(null).commit()
        }

        viewModel.selectedId.observe(this, Observer { id ->
            openFragment(id)
        })

        viewModel.try_search_music.observe(this, Observer { loading ->
            Log.d("TEST", loading.toString())
        })
    }

    private fun openFragment(id: String) {
        val fragment = when (id) {
            R.id.cl_search.toString() -> SearchFragment()
            R.id.cl_playlist.toString() -> MyPlaylistFragment()
            R.id.cl_home.toString() -> HomeFragment()
            R.id.cl_matching.toString() -> MatchingFragment()
            R.id.cl_chatting.toString() -> ChattingFragment()
            else -> null
        }
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fl_main, it).addToBackStack(null).commit()
        }
    }
}