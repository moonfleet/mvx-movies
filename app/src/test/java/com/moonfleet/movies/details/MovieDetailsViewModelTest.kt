package com.moonfleet.movies.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moonfleet.movies.Constants.TEST_MOVIE
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.features.details.state.MovieDetailsState
import com.moonfleet.movies.features.details.viewmodel.MovieDetailsViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.util.TestSchedulerProvider
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import java.util.concurrent.TimeUnit

@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(MockitoJUnitRunner::class)
@PrepareForTest(Movie::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var coordinator: MoviesCoordinator

    @Mock
    lateinit var observer: Observer<MovieDetailsState>

    lateinit var viewModel: MovieDetailsViewModel

    val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieDetailsViewModel(coordinator, TestSchedulerProvider(testScheduler))
    }

    @Test
    fun `Test movie details state lifecycle`() {
        viewModel.viewState.observeForever(observer)
        viewModel.onEmptyState(TEST_MOVIE)
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS)
        verify(observer, times(1)).onChanged(MovieDetailsState.EMPTY)
        verify(observer, times(1)).onChanged(MovieDetailsState.fromPayload(TEST_MOVIE))
        verifyNoMoreInteractions(observer)
    }

    @After
    fun tearDown() {
    }

}