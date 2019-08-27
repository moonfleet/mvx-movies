package com.moonfleet.movies.di

import androidx.lifecycle.ViewModelProvider
import com.moonfleet.movies.annotation.ViewModelKey
import com.moonfleet.movies.base.DaggerAwareViewModelFactory
import com.moonfleet.movies.features.details.viewmodel.MovieDetailsViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.util.BaseSchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory): ViewModelProvider.Factory

}