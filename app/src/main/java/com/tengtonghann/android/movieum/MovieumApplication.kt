package com.tengtonghann.android.movieum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@HiltAndroidApp
class MovieumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}