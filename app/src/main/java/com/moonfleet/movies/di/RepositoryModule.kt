package com.moonfleet.movies.di

import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.repo.DataRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(dataRepository: DataRepository): APIRepository

}