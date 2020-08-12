package com.tengtonghann.android.movieum.di

import com.tengtonghann.android.movieum.data.remote.MovieumService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(ApplicationComponent::class)
@Module
class MovieumApiModule {

    @Singleton
    @Provides
    fun provideRetrofitSingleton(): MovieumService = Retrofit.Builder()
        .baseUrl(MovieumService.MOVIEUM_API_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(AuthInterceptor())
                .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieumService::class.java)


    companion object {
        const val NETWORK_CALL_TIMEOUT = 60
    }
}