package com.suhyeong.yire.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhyeong.yire.activity.DetailActivity
import com.suhyeong.yire.activity.viewmodel.MainViewModel
import com.suhyeong.yire.activity.viewmodel.MainViewModelFactory
import com.suhyeong.yire.adapter.SearchResultAdapter
import com.suhyeong.yire.api.response.SearchResult
import com.suhyeong.yire.databinding.FragmentSearchBinding
import com.suhyeong.yire.listener.ListClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory()).get(MainViewModel::class.java)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.try_search_music.observe(viewLifecycleOwner, Observer { loading ->
            if (loading)
                hideKeyboard(requireActivity())
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { searchResults ->
            searchResults?.let {
                val searchResultAdapter = SearchResultAdapter(it)
                searchResultAdapter.setClickListener(object : ListClickListener {
                    override fun listClickListener(item: SearchResult) {
                        // 노래 정보 화면 띄우기
                        val detailIntent = Intent(requireActivity(), DetailActivity::class.java)
                        detailIntent.putExtra("artistName", item.artistName)
                        detailIntent.putExtra("artworkUrl100", item.artworkUrl100)
                        detailIntent.putExtra("trackCensoredName",  item.trackCensoredName)

                        val trackTimeMillis = item.trackTimeMillis ?: 0L

                        val minutes = (trackTimeMillis / 1000) / 60
                        val seconds = (trackTimeMillis / 1000) % 60
                        val formattedTime = String.format("%02d:%02d", minutes, seconds)

                        detailIntent.putExtra("trackTimeMillis", formattedTime)
                        detailIntent.putExtra("previewUrl", item.previewUrl)
                        detailIntent.putExtra("trackName", item.trackName)

                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val date: Date = inputFormat.parse(item.releaseDate)
                        val releaseDate = outputFormat.format(date)

                        detailIntent.putExtra("releaseDate", releaseDate)

                        startActivity(detailIntent)
                    }
                })
                binding.rvResult.adapter = searchResultAdapter
            }
        })

        return binding.root
    }

    fun hideKeyboard(activity: Activity){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}