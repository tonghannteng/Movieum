package com.tengtonghann.android.movieum

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tengtonghann.android.movieum.data.interceptor.AuthInterceptor
import com.tengtonghann.android.movieum.data.interceptor.ErrorInterceptor
import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.di.MovieumApiModule
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class MovieumServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MovieumService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .addInterceptor(AuthInterceptor())
                    .addInterceptor(ErrorInterceptor())
                    .addNetworkInterceptor(StethoInterceptor())
                    .readTimeout(MovieumApiModule.NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(MovieumApiModule.NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieumService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMoviesTest() = runBlocking {
        enqueueResponse("movies.json")
        val movies = service.getPopularMovie(1).body()

        assertThat(movies, notNullValue())
        if (movies != null) {
            assertThat(movies.movies?.size, `is`(20))
            assertThat(movies.totalResults, `is`(10000))
            assertThat(movies.totalPages, `is`(500))
            assertThat(movies.page, `is`(1))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}