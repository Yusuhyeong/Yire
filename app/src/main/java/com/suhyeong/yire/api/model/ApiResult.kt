package com.suhyeong.yire.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.RawValue

@Parcelize
data class ApiResult<T>(
    @SerializedName("results")
    @Expose
    val results: @RawValue List<T>?,

    @SerializedName("resultCount")
    @Expose
    val resultCount: Int?,

    ) : Parcelable {
    val isSuccess:Boolean
        get() = results != null
}