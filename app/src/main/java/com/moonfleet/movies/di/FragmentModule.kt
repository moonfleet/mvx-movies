package com.moonfleet.movies.di

import androidx.lifecycle.ViewModel
import com.moonfleet.movies.annotation.ViewModelKey
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.details.fragment.MovieDetailsFragment
import com.moonfleet.movies.features.details.state.MovieDetailsState
import com.moonfleet.movies.features.list.fragment.MoviesListFragment
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.features.details.viewmodel.MovieDetailsViewModel
import com.moonfleet.movies.features.list.navigation.MoviesCoordinator
import com.moonfleet.movies.features.list.state.MoviesListState
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.util.BaseSchedulerProvider
import com.moonfleet.movies.util.UISchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun moviesListFragment(): MoviesListFragment

    @ContributesAndroidInjector
    internal abstract fun movieDetailsFragment(): MovieDetailsFragment

}