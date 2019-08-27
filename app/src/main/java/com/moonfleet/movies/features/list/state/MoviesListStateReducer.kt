package com.moonfleet.movies.features.list.state

import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
import com.moonfleet.movies.base.Reducer

class MoviesListStateReducer : Reducer<MoviesListState>(MoviesListState.EMPTY) {

    override fun reduce(initialState: MoviesListState, action: Action): MoviesListState {
        return when(action) {
            is Action.StartLoading -> initialState.copy(loading = true)
            is Action.StopLoading-> initialState.copy(loading = false)
            is Action.AddMovie-> initialState.copy(movies = mutableListOf<MoviePoster>().also { it.add(action.poster) } )
            is Action.ShowError -> MoviesListState.error(action.error)
            else -> throw incompatibleAction(action, initialState)
        }
    }

}