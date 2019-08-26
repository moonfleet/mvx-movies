package com.moonfleet.movies.api.generator

import com.moonfleet.movies.api.service.MovieService

object MovieServiceGenerator : BaseServiceGenerator<MovieService>() {

    val movieService: MovieService
        get() = getService(MovieService::class.java)

}