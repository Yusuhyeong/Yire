package com.suhyeong.yire.api

import com.suhyeong.yire.api.response.ApiResult
import com.suhyeong.yire.api.response.SearchResult
import com.suhyeong.yire.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItsApi {

    // 앱 최신버전 조회
    @GET(Constants.SEARCH_PATH)
    fun searchMusic(
        @Query("term") term: String, // 검색어
        @Query("country") country: String = "KR",
        @Query("media") media: String = "music"
    ): Call<ApiResult<SearchResult>>

}