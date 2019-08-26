package com.moonfleet.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListViewState
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.repo.DataRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MoviesListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    var repository: DataRepository? = null

    @Mock
    var coordinator: MoviesCoordinator? = null

    @Mock
    lateinit var observer: Observer<MoviesListViewState>

    lateinit var viewModel: MoviesListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MoviesListViewModel(repository!!, coordinator!!)
    }

    @Test
    fun `test success state`() {
        `when`(repository!!.getPopularMovies()).thenReturn(Single.just(NetworkResult.MoviePosters(arrayListOf())))
        viewModel.stateLiveData.observeForever(observer)
        verify(observer).onChanged(MoviesListViewState.EMPTY)
        verify(observer).onChanged(MoviesListViewState.LOADING)

    }

    @After
    fun tearDown() {
        repository = null
        coordinator = null
    }
}
