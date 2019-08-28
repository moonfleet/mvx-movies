package com.moonfleet.movies.features.list.state

import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
import com.moonfleet.movies.base.Reducer
import timber.log.Timber

class MoviesListStateReducer : Reducer<MoviesListState>(MoviesListState.EMPTY) {

    override fun reduce(initialState: MoviesListState, action: Action): MoviesListState {
        Timber.e("Action $action to $initialState")
        return when(action) {
            is Action.StartLoading -> initialState.copy(loading = true)
            is Action.StopLoading -> initialState.copy(loading = false)
            is Action.AddMovie -> {
                val movies = mutableListOf<MoviePoster>()
                initialState.movies?.let {
                    movies.addAll(initialState.movies)
                }
                movies.add(action.poster)
                initialState.copy(movies = movies)
            }
            is Action.ShowError -> MoviesListState.error(action.error)
            else -> throw incompatibleAction(action, initialState)
        }
    }

}