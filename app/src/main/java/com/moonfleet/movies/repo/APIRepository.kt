package com.moonfleet.movies.repo

import com.moonfleet.movies.NetworkResult
import com.moonfleet.movies.api.model.GetMoviesResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

interface APIRepository {

    fun getPopularMovies(): Single<NetworkResult>
    fun getUpcomingMovies(): Single<Response<GetMoviesResponse>>

}