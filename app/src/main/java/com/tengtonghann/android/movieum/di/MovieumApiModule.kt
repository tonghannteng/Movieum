package com.tengtonghann.android.movieum.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tengtonghann.android.movieum.data.dao.CastsDao
import com.tengtonghann.android.movieum.data.dao.MoviesDao
import com.tengtonghann.android.movieum.data.dao.ReviewsDao
import com.tengtonghann.android.movieum.data.dao.TrailersDao
import com.tengtonghann.android.movieum.data.interceptor.AuthInterceptor
import com.tengtonghann.android.movieum.data.interceptor.ErrorInterceptor
import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.data.repository.MoviesRepository
import com.tengtonghann.android.movieum.data.repository.MoviesRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(ApplicationComponent::class)
@Module
@ExperimentalCoroutinesApi
class MovieumApiModule {

    @Singleton
    @Provides
    fun provideRetrofitSingleton(): MovieumService = Retrofit.Builder()
        .baseUrl(MovieumService.MOVIEUM_API_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(AuthInterceptor())
                .addInterceptor(ErrorInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieumService::class.java)


    @Singleton
    @Provides
    fun injectRepository(
         movieumService: MovieumService,
         moviesDao: MoviesDao,
         castsDao: CastsDao,
         reviewsDao: ReviewsDao,
         trailersDao: TrailersDao
    ) = MoviesRepository(movieumService, moviesDao, castsDao, reviewsDao, trailersDao) as MoviesRepositoryInterface

    companion object {
        const val NETWORK_CALL_TIMEOUT = 60
    }
}