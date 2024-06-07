package com.suhyeong.yire.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class SearchResult(
    @SerializedName("wrapperType")
    @Expose
    val wrapperType: String?,

    @SerializedName("kind")
    @Expose
    val kind: String?,

    @SerializedName("collectionId")
    @Expose
    val collectionId: Long?,

    @SerializedName("trackId")
    @Expose
    val trackId: Long?,

    @SerializedName("artistName")
    @Expose
    val artistName: String?,

    @SerializedName("collectionName")
    @Expose
    val collectionName: String?,

    @SerializedName("trackName")
    @Expose
    val trackName: String?,

    @SerializedName("collectionCensoredName")
    @Expose
    val collectionCensoredName: String?,

    @SerializedName("trackCensoredName")
    @Expose
    val trackCensoredName: String?,

    @SerializedName("collectionArtistId")
    @Expose
    val collectionArtistId: Long?,

    @SerializedName("collectionArtistViewUrl")
    @Expose
    val collectionArtistViewUrl: String?,

    @SerializedName("collectionViewUrl")
    @Expose
    val collectionViewUrl: String?,

    @SerializedName("trackViewUrl")
    @Expose
    val trackViewUrl: String?,

    @SerializedName("previewUrl")
    @Expose
    val previewUrl: String?,

    @SerializedName("artworkUrl30")
    @Expose
    val artworkUrl30: String?,

    @SerializedName("artworkUrl60")
    @Expose
    val artworkUrl60: String?,

    @SerializedName("artworkUrl100")
    @Expose
    val artworkUrl100: String?,

    @SerializedName("collectionPrice")
    @Expose
    val collectionPrice: Double?,

    @SerializedName("trackPrice")
    @Expose
    val trackPrice: Double?,

    @SerializedName("trackRentalPrice")
    @Expose
    val trackRentalPrice: Double?,

    @SerializedName("collectionHdPrice")
    @Expose
    val collectionHdPrice: Double?,

    @SerializedName("trackHdPrice")
    @Expose
    val trackHdPrice: Double?,

    @SerializedName("trackHdRentalPrice")
    @Expose
    val trackHdRentalPrice: Double?,

    @SerializedName("releaseDate")
    @Expose
    val releaseDate: String?,

    @SerializedName("collectionExplicitness")
    @Expose
    val collectionExplicitness: String?,

    @SerializedName("trackExplicitness")
    @Expose
    val trackExplicitness: String?,

    @SerializedName("discCount")
    @Expose
    val discCount: Int?,

    @SerializedName("discNumber")
    @Expose
    val discNumber: Int?,

    @SerializedName("trackCount")
    @Expose
    val trackCount: Int?,

    @SerializedName("trackNumber")
    @Expose
    val trackNumber: Int?,

    @SerializedName("trackTimeMillis")
    @Expose
    val trackTimeMillis: Long?,

    @SerializedName("country")
    @Expose
    val country: String?,

    @SerializedName("currency")
    @Expose
    val currency: String?,

    @SerializedName("primaryGenreName")
    @Expose
    val primaryGenreName: String?,

    @SerializedName("contentAdvisoryRating")
    @Expose
    val contentAdvisoryRating: String?,

    @SerializedName("shortDescription")
    @Expose
    val shortDescription: String?,

    @SerializedName("longDescription")
    @Expose
    val longDescription: String?,

    @SerializedName("hasITunesExtras")
    @Expose
    val hasITunesExtras: Boolean?
) : Parcelable