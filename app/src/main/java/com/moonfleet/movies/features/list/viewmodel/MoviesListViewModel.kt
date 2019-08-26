package com.moonfleet.movies.features.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moonfleet.movies.NetworkResult
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListViewState
import com.moonfleet.movies.messageOrUnknown
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.startWith
import com.moonfleet.movies.util.BaseSchedulerProvider
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MoviesListViewModel @Inject constructor(
    private val repository: APIRepository,
    private val coordinator: MoviesCoordinator,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val stateLiveData: MutableLiveData<MoviesListViewState> = MutableLiveData<MoviesListViewState>().startWith(
        MoviesListViewState.EMPTY
    )

    fun onEmptyState() {
        repository.getPopularMovies()
            .doOnSubscribe { stateLiveData.postValue(MoviesListViewState.LOADING) }
            .map { result ->
                when (result) {
                    is NetworkResult.MoviePosters -> MoviesListViewState.fromPayload(result.payload)
                    is NetworkResult.HttpError -> MoviesListViewState.error("${result.code}: ${result.message}")
                    else -> MoviesListViewState.error("Unexpected result")
                }
            }
            .onErrorReturn { MoviesListViewState.error(it.messageOrUnknown()) }
            .safeSubscribe({ state -> stateLiveData.value = state })
    }

    fun onMovieClick(moviePoster: MoviePoster) {
        coordinator.showMovieDetails(moviePoster.movie)
    }

}