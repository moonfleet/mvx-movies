package com.moonfleet.movies.di

import androidx.lifecycle.ViewModelProvider
import com.moonfleet.movies.base.DaggerAwareViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory): ViewModelProvider.Factory
}