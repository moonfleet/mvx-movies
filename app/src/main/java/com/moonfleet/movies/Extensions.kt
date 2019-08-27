package com.moonfleet.movies

import androidx.lifecycle.MutableLiveData
import com.moonfleet.movies.api.model.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response

sealed class NetworkResult {
    data class MoviePosters(val payload: List<MoviePoster>) : NetworkResult()
    data class Movies(val payload: List<Movie>) : NetworkResult()
    data class Genres(val payload: List<Genre>) : NetworkResult()
    data class HttpError(val code: Int, val message: String) : NetworkResult()
}

fun Response<GetMoviesResponse>.toMoviesResult(): NetworkResult =
    if (this.isSuccessful && this.body() != null) {
        NetworkResult.Movies(this.body()!!.movies)
    } else {
        NetworkResult.HttpError(this.code(), this.message())
    }

fun Response<Genres>.toGenresResult(): NetworkResult =
    if (this.isSuccessful && this.body() != null) {
        NetworkResult.Genres(this.body()!!.genres)
    } else {
        NetworkResult.HttpError(this.code(), this.message())
    }

fun <T> MutableLiveData<T>.startWith(initialValue: T) = apply { value = initialValue }

fun Throwable.messageOrUnknown() = this.message ?: "Unknown error"

operator fun CompositeDisposable.plusAssign(anotherDisposable: Disposable?) {
    anotherDisposable?.let(::add)
}

