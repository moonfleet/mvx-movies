package com.moonfleet.movies.repo

import com.moonfleet.movies.NetworkResult
import com.moonfleet.movies.api.model.GetMoviesResponse
import com.moonfleet.movies.api.model.MoviePoster
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

interface APIRepository {

    fun getPopularMovies(): Flowable<MoviePoster>
    fun getUpcomingMovies(): Flowable<MoviePoster>
    fun getGenres(): Flowable<NetworkResult>

}