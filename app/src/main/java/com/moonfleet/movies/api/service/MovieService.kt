package com.moonfleet.movies.api.service

import com.moonfleet.movies.api.model.Genres
import com.moonfleet.movies.api.model.GetMoviesResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/upcoming")
    fun getPopular(@Query("page") page: Int): Flowable<Response<GetMoviesResponse>>

    @GET("genre/movie/list")
    fun getGenres(): Flowable<Response<Genres>>

}