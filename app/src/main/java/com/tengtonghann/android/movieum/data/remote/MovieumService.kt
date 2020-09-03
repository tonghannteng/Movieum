package com.tengtonghann.android.movieum.data.remote

import com.tengtonghann.android.movieum.data.remote.MovieumService.Companion.MOVIEUM_API_URL
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.MovieDetail
import com.tengtonghann.android.movieum.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Network Service to fetch data from endpoint [MOVIEUM_API_URL]
 */
interface MovieumService {

    @GET("3/movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int): Response<MoviesResponse>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovie(@Query("page") page: Int): Response<MoviesResponse>

    @GET("3/movie/{id}?append_to_response=videos,credits,reviews")
    suspend fun getMovieDetail(@Path("id") id: Long): Response<Movie>

    companion object {
        const val MOVIEUM_API_URL = "https://api.themoviedb.org/"
    }
}