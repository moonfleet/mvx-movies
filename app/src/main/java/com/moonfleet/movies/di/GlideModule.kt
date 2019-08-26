package com.moonfleet.movies.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.moonfleet.movies.repo.APIRepository
import com.moonfleet.movies.repo.DataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class GlideModule {

    @Provides
    fun provideGlide(context: Context): RequestManager = Glide.with(context)

}