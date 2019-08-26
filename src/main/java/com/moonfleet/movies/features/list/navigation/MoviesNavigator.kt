package com.moonfleet.movies.features.list.navigation

import com.moonfleet.movies.R
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.base.BaseNavigator
import com.moonfleet.movies.features.list.fragment.MoviesListFragmentDirections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesNavigator @Inject constructor() : BaseNavigator() {

    fun showMovieDetails(movie: Movie) {
        navController() to MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(movie)
    }

    fun closeScreen() {
        activity?.finish()
    }

    fun closeMovieDetails() {
        navController()?.popBackStack(R.id.movieDetailsFragment, true)
    }

}