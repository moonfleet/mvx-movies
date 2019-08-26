package com.moonfleet.movies.features.list.state

import com.moonfleet.movies.api.model.MoviePoster

data class MoviesListViewState private constructor(val loading: Boolean, val movies: List<MoviePoster>? = null, val error: String? = null) {

    companion object {
        val EMPTY = MoviesListViewState(false)
        val LOADING = MoviesListViewState(true)

        fun fromPayload(payload: List<MoviePoster>) = EMPTY.copy(movies = payload)
        fun error(error: String) = EMPTY.copy(error = error)
    }

}