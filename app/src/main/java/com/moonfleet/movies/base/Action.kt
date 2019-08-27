package com.moonfleet.movies.base

import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster

sealed class Action {

    object StartLoading : Action()
    data class AddMovie(val poster: MoviePoster) : Action()
    object StopLoading : Action()
    data class ShowError(val error: String) : Action()
    data class DisplayMovieDetails(val movie: Movie) : Action()

}