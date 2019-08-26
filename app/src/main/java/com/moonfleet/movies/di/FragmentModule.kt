package com.moonfleet.movies.di

import com.moonfleet.movies.annotation.ViewModelKey
import com.moonfleet.movies.base.BaseViewModel
import com.moonfleet.movies.features.details.fragment.MovieDetailsFragment
import com.moonfleet.movies.features.list.fragment.MoviesListFragment
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.features.details.viewmodel.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class FragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    abstract fun bindMoviesViewModel(viewModel: MoviesListViewModel): BaseViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): BaseViewModel

    @ContributesAndroidInjector
    internal abstract fun moviesListFragment(): MoviesListFragment

    @ContributesAndroidInjector
    internal abstract fun movieDetailsFragment(): MovieDetailsFragment

}