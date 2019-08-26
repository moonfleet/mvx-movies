package com.moonfleet.movies.features.list.navigation

import com.moonfleet.movies.api.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesCoordinator @Inject constructor(val navigator: MoviesNavigator) {

    fun showMovieDetails(movie: Movie) {
        navigator.showMovieDetails(movie)
    }

    fun closeScreen() {
        navigator.closeScreen()
    }

    fun closeMovieDetails() {
        navigator.closeMovieDetails()
    }

}