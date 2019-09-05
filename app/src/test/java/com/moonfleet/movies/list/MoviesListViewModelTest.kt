package com.moonfleet.movies.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListState
import com.moonfleet.movies.features.list.state.MoviesListStateReducer
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.repo.DataRepository
import com.moonfleet.movies.util.TestSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit


class MoviesListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: DataRepository

    @Mock
    lateinit var coordinator: MoviesCoordinator

    @Mock
    lateinit var observer: Observer<MoviesListState>

    @Mock
    lateinit var moviePoster: MoviePoster

    lateinit var viewModel: MoviesListViewModel

    val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MoviesListViewModel(repository, coordinator, TestSchedulerProvider(testScheduler))
    }

    @Test
    fun `Test movies list state lifecycle`() {
        `when`(repository.getPopularMovies()).thenReturn(Flowable.fromIterable(mutableListOf(moviePoster)))
        viewModel.viewState.observeForever(observer)
        viewModel.onEmptyState()
        testScheduler.advanceTimeBy(365, TimeUnit.DAYS)
        verify(observer, times(1)).onChanged(MoviesListState.EMPTY)
        verify(observer, times(1)).onChanged(MoviesListState.LOADING)
        verify(observer, times(1)).onChanged(MoviesListState.fromPayload(mutableListOf(moviePoster)).copy(loading = true))
        verify(observer, times(1)).onChanged(MoviesListState.fromPayload(mutableListOf(moviePoster)).copy(loading = false))
        verifyNoMoreInteractions(observer)
    }

    @After
    fun tearDown() {
    }
}