package com.moonfleet.movies.features.details.state

import com.moonfleet.movies.base.Action
import com.moonfleet.movies.base.Reducer

class MovieDetailsStateReducer : Reducer<MovieDetailsState>(MovieDetailsState.EMPTY) {

    override fun reduce(initialState: MovieDetailsState, action: Action): MovieDetailsState {
        return when(action) {
            is Action.DisplayMovieDetails -> initialState.copy(movie = action.movie)
            else -> throw incompatibleAction(action, initialState)
        }
    }

}