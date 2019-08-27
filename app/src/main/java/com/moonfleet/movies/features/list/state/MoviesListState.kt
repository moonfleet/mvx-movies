package com.moonfleet.movies.features.list.state

import com.moonfleet.movies.api.model.MoviePoster

data class MoviesListState private constructor(val loading: Boolean, val movies: List<MoviePoster>? = null, val error: String? = null) {

    companion object {
        val EMPTY = MoviesListState(false)
        val LOADING = MoviesListState(true)

        fun fromPayload(payload: List<MoviePoster>) = EMPTY.copy(movies = payload)
        fun error(error: String) = EMPTY.copy(error = error)
    }

}