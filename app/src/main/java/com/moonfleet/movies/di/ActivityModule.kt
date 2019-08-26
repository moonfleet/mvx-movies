package com.moonfleet.movies.di

import com.moonfleet.movies.base.BaseNavigator
import com.moonfleet.movies.features.list.activity.MoviesActivity
import com.moonfleet.movies.features.list.navigation.MoviesNavigator
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {

    @Binds
    abstract fun bindNavigator(moviesNavigator: MoviesNavigator): BaseNavigator

    @ContributesAndroidInjector
    internal abstract fun moviesActivity(): MoviesActivity

}