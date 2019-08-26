package com.moonfleet.movies.di

import com.moonfleet.movies.api.generator.MovieServiceGenerator
import com.moonfleet.movies.api.service.MovieService
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    fun providesMovieService(): MovieService {
        return MovieServiceGenerator.movieService
    }
}