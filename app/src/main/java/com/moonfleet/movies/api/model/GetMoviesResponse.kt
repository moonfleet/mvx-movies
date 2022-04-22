package com.moonfleet.movies.api.model

import android.os.Parcel
import android.os.Parcelable
import androidx.palette.graphics.Palette
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetMoviesResponse(val page: Int, @SerializedName("results") val movies: List<Movie>,
                             val dates: Dates,
                             @SerializedName("total_pages") val totalPages: Int,
                             @SerializedName("total_results") val totalResults: Int)
@Parcelize
data class Movie(
    val title: String,
    val overview: String, @SerializedName("poster_path") val posterPath: String, @SerializedName(
        "genre_ids"
    ) val genreIds: List<Int>
) : Parcelable

data class Dates(@SerializedName("minimum") val min: String, @SerializedName("maximum") val max: String)
