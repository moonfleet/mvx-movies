package com.moonfleet.movies.features.details.state

import com.moonfleet.movies.api.model.Movie

data class MovieDetailsState private constructor(val movie: Movie? = null) {

    companion object {
        val EMPTY = MovieDetailsState()

        fun fromPayload(payload: Movie) = EMPTY.copy(movie = payload)
    }

}