package com.tengtonghann.android.movieum.di

import android.app.Application
import com.tengtonghann.android.movieum.data.db.MovieumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class MovieumDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = MovieumDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePopularMovieDao(database: MovieumDatabase) = database.getMoviesMovieDao()

    @Singleton
    @Provides
    fun provideCastsDao(database: MovieumDatabase) = database.getCastsDao()

    @Singleton
    @Provides
    fun provideReviewsDao(database: MovieumDatabase) = database.getReviewsDao()

    @Singleton
    @Provides
    fun provideTrailersDao(database: MovieumDatabase) = database.getTrailersDao()
}