package com.moonfleet.movies.di

import com.moonfleet.movies.app.MoviesApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelFactoryModule::class,
        ServiceModule::class,
        RepositoryModule::class,
        SchedulersModule::class,
        GlideModule::class]
)
interface AppComponent : AndroidInjector<MoviesApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MoviesApplication>()

}