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
                        val intent = Intent(requireActivity(), DetailActivity::class.java)
                        startActivity(intent)
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