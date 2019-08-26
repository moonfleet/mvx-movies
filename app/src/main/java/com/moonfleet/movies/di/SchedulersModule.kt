package com.moonfleet.movies.di

import com.moonfleet.movies.util.BaseSchedulerProvider
import com.moonfleet.movies.util.UISchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class SchedulersModule {

    @Binds
    abstract fun bindSchedulerProvider(uiSchedulerProvider: UISchedulerProvider): BaseSchedulerProvider

}