package com.moonfleet.movies.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.details.state.MovieDetailsState
import com.moonfleet.movies.features.details.state.MovieDetailsStateReducer
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.startWith
import com.moonfleet.movies.util.BaseSchedulerProvider
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val coordinator: MoviesCoordinator,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel<MovieDetailsState>(schedulerProvider, MovieDetailsStateReducer()) {

    val title: LiveData<String>
    val overview: LiveData<String>

    init {
        title = Transformations.map(viewState) { state -> state.movie?.title ?: "n/a" }
        overview = Transformations.map(viewState) { state -> state.movie?.overview ?: "n/a" }
    }

    fun onCloseClick() {
        coordinator.closeMovieDetails()
    }

    fun onEmptyState(movie: Movie) {
        onAction(Action.DisplayMovieDetails(movie))
    }

}