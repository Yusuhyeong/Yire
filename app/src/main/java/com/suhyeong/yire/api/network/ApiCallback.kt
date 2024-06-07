package com.suhyeong.yire.api.network

import android.util.Log
import com.suhyeong.yire.api.ApiError
import com.suhyeong.yire.api.ApiErrorCause
import com.suhyeong.yire.api.model.ApiResult
import com.suhyeong.yire.utils.origin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * @suppress
 */
abstract class ApiCallback<T : Any> : Callback<ApiResult<T>> {
    private val TAG: String
        get() = "ApiCallback"

    abstract fun onComplete(model: List<T>?, error: Throwable?)

    override fun onFailure(call: Call<ApiResult<T>>, t: Throwable) {
        t.origin.also {
//            FirebaseCrashlytics.getInstance().recordException(it)
            Log.d(TAG, "$it")
            onComplete(null, it)
        }
    }

    override fun onResponse(call: Call<ApiResult<T>>, response: Response<ApiResult<T>>) {
        val commonResponse = response.body()
        val model = commonResponse?.results

        if (commonResponse != null) {
            Log.d(TAG, "response = $commonResponse")
            // api에서 성공인데 data = null 인 case가 있어 분기처리 필요
            if (commonResponse.isSuccess) {
                onComplete(model, null)
            } else {
                onComplete(
                    model,
                    ApiError(
                        response.code(),
                        ApiErrorCause.InternalError,
                        "아이튠즈 API 오류"
                    )
                )
            }
        } else {
            onFailure(call,
                translateError(
                    HttpException(response)
                )
            )
        }
    }

    companion object {
        fun translateError(t: Throwable): Throwable {
            try {
                if (t is HttpException) {
                    val errorString = t.response()?.errorBody()?.string()
                    val response =
                        ItsJson.fromJson<ApiResult<Any>>(
                            errorString!!,
                            ApiResult::class.java
                        )
                    val cause =
                        ItsJson.fromJson(
                            t.code().toString(),
                            ApiErrorCause::class.java
                        )
                            ?: ApiErrorCause.Unknown
                    return ApiError(t.code(), cause, t.message())
                }
                return t
            } catch (unexpected: Throwable) {
                return unexpected
            }
        }
    }
}
