package com.moonfleet.movies.base

import com.moonfleet.movies.api.model.MoviePoster

sealed class Action {

    object StartLoading : Action()
    data class AddMovie(val poster: MoviePoster) : Action()
    object StopLoading : Action()
    data class ShowError(val error: String) : Action()

}