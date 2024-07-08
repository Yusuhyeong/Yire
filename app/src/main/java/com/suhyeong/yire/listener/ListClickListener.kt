package com.suhyeong.yire.listener

import com.suhyeong.yire.api.response.SearchResult

interface ListClickListener {
    fun listClickListener(item: SearchResult)
}