package com.moonfleet.movies.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.moonfleet.movies.annotation.ViewModelKey
import com.moonfleet.movies.base.DaggerAwareViewModelFactory
import com.moonfleet.movies.features.details.viewmodel.MovieDetailsViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.repo.DataRepository
import com.moonfleet.movies.util.BaseSchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
abstract class ViewModelsModule {

//    @Provides
//    fun provideViewModelFactory(creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>) = DaggerAwareViewModelFactory(creators)
//
    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    abstract fun bindMoviesListViewModel(viewMode: MoviesListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewMode: MovieDetailsViewModel) : ViewModel

}