package com.moonfleet.movies.features.details.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.startWith
import com.moonfleet.movies.util.BaseSchedulerProvider
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val repository: APIRepository,
    private val coordinator: MoviesCoordinator,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val titleLiveData: MutableLiveData<String> = MutableLiveData<String>().startWith("Loading...")
    val overviewLiveData: MutableLiveData<String> = MutableLiveData<String>().startWith("Loading...")

    fun onCloseClick() {
        coordinator.closeMovieDetails()
    }

    fun onEmptyState(title: String, overview: String) {
        titleLiveData.value = title
        overviewLiveData.value = overview
    }

}