package com.moonfleet.movies.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.Action
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


class MoviesListStateReducerTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val incompatibleActionExceptionRule : ExpectedException = ExpectedException.none()

    @Mock
    lateinit var moviePoster: MoviePoster

    @Mock
    lateinit var movie: Movie

    val reducer = MoviesListStateReducer()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Test reducer Empty to Loading`() {
        val state = reducer.reduce(MoviesListState.EMPTY, Action.StartLoading)
        assertEquals("Unexpected state", MoviesListState.LOADING, state)
    }

    @Test
    fun `Test reducer Empty to Payload`() {
        val state = reducer.reduce(MoviesListState.EMPTY, Action.AddMovie(moviePoster))
        assertEquals("Unexpected state", MoviesListState.fromPayload(mutableListOf(moviePoster)), state)
    }

    @Test
    fun `Test reducer Loading to Payload`() {
        val state = reducer.reduce(MoviesListState.LOADING, Action.AddMovie(moviePoster))
        assertEquals("Unexpected state", MoviesListState.fromPayload(mutableListOf(moviePoster)).copy(loading = true), state)
    }

    @Test
    fun `Test reducer Loading to Empty`() {
        var state = reducer.reduce(MoviesListState.EMPTY, Action.StartLoading)
        state = reducer.reduce(state, Action.StopLoading)
        assertEquals("Unexpected state", MoviesListState.EMPTY, state)
    }

    @Test
    fun `Test reducer incompatible action`() {
        incompatibleActionExceptionRule.expect(IllegalStateException::class.java)
        incompatibleActionExceptionRule.expectMessage("unable to reduce state")
        reducer.reduce(MoviesListState.EMPTY, Action.DisplayMovieDetails(movie))
    }

    @After
    fun tearDown() {
    }
}
