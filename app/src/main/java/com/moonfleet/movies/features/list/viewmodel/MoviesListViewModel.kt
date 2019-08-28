package com.moonfleet.movies.features.list.viewmodel

import com.moonfleet.movies.NetworkResult
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListState
import com.moonfleet.movies.features.list.state.MoviesListStateReducer
import com.moonfleet.movies.messageOrUnknown
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.repo.DataRepository
import com.moonfleet.movies.toMoviesResult
import com.moonfleet.movies.util.BaseSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val repository: APIRepository,
    private val coordinator: MoviesCoordinator,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel<MoviesListState>(schedulerProvider, MoviesListStateReducer()) {

    fun onEmptyState() {
        repository.getPopularMovies()
            .map<Action> { poster ->
                Timber.e("$poster")
                Action.AddMovie(poster)
            }
            .startWith(Action.StartLoading)
            .onErrorReturn { error -> Action.ShowError(error.messageOrUnknown()) }
            .doOnComplete { onAction(Action.StopLoading) }
            .safeSubscribe(::onAction)
    }

    fun onMovieClick(moviePoster: MoviePoster) {
        coordinator.showMovieDetails(moviePoster.movie)
    }

}