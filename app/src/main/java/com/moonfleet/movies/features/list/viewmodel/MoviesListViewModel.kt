package com.moonfleet.movies.features.list.viewmodel

import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListState
import com.moonfleet.movies.features.list.state.MoviesListStateReducer
import com.moonfleet.movies.messageOrUnknown
import com.moonfleet.movies.plusAssign
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.util.BaseSchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val repository: APIRepository,
    private val coordinator: MoviesCoordinator,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel<MoviesListState>(schedulerProvider, MoviesListStateReducer()) {

    fun onEmptyState() {
        compositeDisposable += repository.getPopularMovies()
            .map<Action> { poster ->
                Timber.e("$poster")
                Action.AddMovie(poster)
            }
            .startWith(Action.StartLoading)
            .onErrorReturn { error -> Action.ShowError(error.messageOrUnknown()) }
            .scheduleAsync()
            .doFinally { onAction(Action.StopLoading) }
            .subscribe(::onAction)
    }

    fun onMovieClick(moviePoster: MoviePoster) {
        coordinator.showMovieDetails(moviePoster.movie)
    }

}