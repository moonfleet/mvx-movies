package com.moonfleet.movies

import androidx.lifecycle.MutableLiveData
import com.moonfleet.movies.api.model.GetMoviesResponse
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import retrofit2.Response

sealed class NetworkResult {
    data class MoviePosters(val payload: List<MoviePoster>) : NetworkResult()
    data class Movies(val payload: List<Movie>) : NetworkResult()
    data class HttpError(val code: Int, val message: String) : NetworkResult()
}

fun Response<GetMoviesResponse>.toNetworkResult(): NetworkResult =
    if (this.isSuccessful && this.body() != null) {
        NetworkResult.Movies(this.body()!!.movies)
    } else {
        NetworkResult.HttpError(this.code(), this.message())
    }

fun <T> MutableLiveData<T>.startWith(initialValue: T) = apply { value = initialValue }

fun Throwable.messageOrUnknown() = this.message ?: "Unknown error"

