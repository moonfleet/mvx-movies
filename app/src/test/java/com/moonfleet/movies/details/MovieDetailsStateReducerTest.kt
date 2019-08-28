package com.moonfleet.movies.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moonfleet.movies.Constants.TEST_MOVIE
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
import com.moonfleet.movies.features.details.state.MovieDetailsState
import com.moonfleet.movies.features.details.state.MovieDetailsStateReducer
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListState
import com.moonfleet.movies.features.list.state.MoviesListStateReducer
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.repo.DataRepository
import com.moonfleet.movies.util.TestSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class MovieDetailsStateReducerTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val incompatibleActionExceptionRule : ExpectedException = ExpectedException.none()

    val reducer = MovieDetailsStateReducer()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Test reducer Empty to Payload`() {
        val state = reducer.reduce(MovieDetailsState.EMPTY, Action.DisplayMovieDetails(TEST_MOVIE))
        assertEquals("Unexpected state", MovieDetailsState.fromPayload(TEST_MOVIE), state)
    }

    @Test
    fun `Test reducer incompatible action`() {
        incompatibleActionExceptionRule.expect(IllegalStateException::class.java)
        incompatibleActionExceptionRule.expectMessage("unable to reduce state")
        reducer.reduce(MovieDetailsState.EMPTY, Action.StartLoading)
    }

    @After
    fun tearDown() {
    }
}
