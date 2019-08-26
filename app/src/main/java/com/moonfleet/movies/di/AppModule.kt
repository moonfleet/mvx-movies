package com.moonfleet.movies.di

import android.content.Context
import com.moonfleet.movies.app.MoviesApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesContext(application: MoviesApplication): Context {
        return application.applicationContext
    }
}