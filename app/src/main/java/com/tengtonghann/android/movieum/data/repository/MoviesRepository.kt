package com.tengtonghann.android.movieum.data.repository

import com.tengtonghann.android.movieum.data.dao.MoviesDao
import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [MoviesRepository] is a Singleton class for fetching data from network and
 * storing in database for offline.
 */

@ExperimentalCoroutinesApi
@Singleton
class MoviesRepository @Inject constructor(
    private val movieumService: MovieumService,
    private val moviesDao: MoviesDao
) {

    /**
     * Fetched data from network and stored in database.
     * data from persistence is fetched and emitted.
     */
    fun getPopularMovies(page: Int): Flow<State<List<Movie>>> {

        return object : NetworkBoundRepository<List<Movie>, MoviesResponse>() {

            override suspend fun saveNetworkData(response: MoviesResponse) {
                response.movies?.let { moviesDao.insertPopularMovies(it) }
            }

            override suspend fun fetchFromDatabase(): Flow<List<Movie>> =
                moviesDao.getAllPopularMovies()

            override suspend fun fetchMovieFromNetwork(): Response<MoviesResponse> =
                movieumService.getPopularMovie(page)

        }.asFlow().flowOn(Dispatchers.IO)
    }

    /**
     * Fetched data from network and stored in database.
     * data from persistence is fetched and emitted.
     */
    fun getTopRatedMovies(page: Int): Flow<State<List<TopRatedMovie>>> {

        return object : NetworkBoundRepository<List<TopRatedMovie>, TopRatedResponse>() {

            override suspend fun saveNetworkData(response: TopRatedResponse) {
                response.movies?.let { moviesDao.insertTopRatedMovies(it) }
            }

            override suspend fun fetchFromDatabase(): Flow<List<TopRatedMovie>> =
                moviesDao.getAllTopRatedMovies()

            override suspend fun fetchMovieFromNetwork(): Response<TopRatedResponse> =
                movieumService.getTopRatedMovie(page)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}