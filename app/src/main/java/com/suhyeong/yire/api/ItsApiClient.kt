package com.suhyeong.yire.api

import com.suhyeong.yire.api.model.SearchResult
import com.suhyeong.yire.api.network.ApiCallback
import com.suhyeong.yire.api.network.ApiFactory

/**
 * ITunesSearch API 호출을 담당하는 클라이언트.
 */
class ItsApiClient (
    private val itsApi: ItsApi = ApiFactory.itsApi.create(ItsApi::class.java)
) {
    /**
     * 앱 최신버전 조회
     */
    fun searchMusic(term: String, callback: (results: List<SearchResult>?, error: Throwable?) -> Unit) {
        itsApi.searchMusic(term)
            .enqueue(object : ApiCallback<SearchResult>() {
                override fun onComplete(model: List<SearchResult>?, error: Throwable?) {
                    callback(model, error)
                }
            })
    }


    companion object {
        /**
         * 간편한 API 호출을 위해 기본 제공되는 singleton 객체
         */
        @JvmStatic
        val instance by lazy { ItsApiClient() }
    }
}
