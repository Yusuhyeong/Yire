package com.suhyeong.yire.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhyeong.yire.adapter.SearchResultAdapter
import com.suhyeong.yire.api.response.SearchResult
import com.suhyeong.yire.databinding.FragmentSearchBinding
import com.suhyeong.yire.fragment.viewmodel.FragmentViewModel
import com.suhyeong.yire.listener.ListClickListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        val viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.result.observe(viewLifecycleOwner, Observer { searchResults ->
            searchResults?.let {
                for (i: Int in 0 until searchResults.size) {
                    Log.d("결과", "==============================================")
                    Log.d("결과", "가수 : " + searchResults.get(i).artistName.toString())
                    Log.d("결과", "제목 : " + searchResults.get(i).trackCensoredName.toString())
                    Log.d("결과", "해당 앨범 : " + searchResults.get(i).trackName.toString())
                    Log.d("결과", "재생 시간 : " + searchResults.get(i).trackTimeMillis.toString())
                    Log.d("결과", "앨범 커버 : " + searchResults.get(i).artworkUrl100.toString())
                    Log.d("결과", "출시일 : " + searchResults.get(i).releaseDate.toString())
                    Log.d("결과", "장르 : " + searchResults.get(i).primaryGenreName.toString())
                    Log.d("결과", "==============================================")
                }
                val searchResultAdapter = SearchResultAdapter(it)
                searchResultAdapter.setClickListener(object : ListClickListener {
                    override fun listClickListener(item: SearchResult) {
                        // 노래 정보 화면 띄우기
                    }
                })
                binding.rvResult.adapter = searchResultAdapter
            }
        })

        return binding.root
    }
}