package com.moonfleet.movies.api.model

import android.os.Parcel
import android.os.Parcelable
import androidx.palette.graphics.Palette
import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(val page: Int, @SerializedName("results") val movies: List<Movie>,
                             val dates: Dates,
                             @SerializedName("total_pages") val totalPages: Int,
                             @SerializedName("total_results") val totalResults: Int)

data class Movie(val title: String, val overview: String, @SerializedName("poster_path") val posterPath: String) :
    Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(overview)
        writeString(posterPath)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}

data class Dates(@SerializedName("minimum") val min: String, @SerializedName("maximum") val max: String)
