package com.tengtonghann.android.movieum.data.repository

import com.tengtonghann.android.movieum.data.dao.MoviesDao
import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.model.*
import com.tengtonghann.android.movieum.utils.Logger
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
                response.movies?.let {
                    moviesDao.insertMovies(it)
                    /**
                     * TODO: Find a good mechanism insert Popular Table
                     * Current Implement: Loop through [Movie] and update Popular Column
                     */
                    it.forEach { movie ->
                        moviesDao.updatePopularMovie(movieId = movie.id)
                    }
                }
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
    fun getTopRatedMovies(page: Int): Flow<State<List<Movie>>> {

        return object : NetworkBoundRepository<List<Movie>, MoviesResponse>() {

            override suspend fun saveNetworkData(response: MoviesResponse) {
                response.movies?.let {
                    moviesDao.insertMovies(it)
                    /**
                     * TODO: Find a good mechanism insert Favorite Table
                     * Current Implement: Loop through [Movie] and update Top Rated Column
                     */
                    it.forEach { movie ->
                        moviesDao.updateTopRatedMovie(movieId = movie.id)
                    }
                }
            }

            override suspend fun fetchFromDatabase(): Flow<List<Movie>> =
                moviesDao.getAllTopRatedMovies()

            override suspend fun fetchMovieFromNetwork(): Response<MoviesResponse> =
                movieumService.getTopRatedMovie(page)

        }.asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun addFavoriteMovie(movie: Movie) {
        /**
         * TODO: Find a way to convert [Movie] and [FavoriteMovie] object, both have the same object
         * Current implement set [Movie] properties to [FavoriteMovie]
         */
        val favoriteMovie = FavoriteMovie(movie.id, movie.title, movie.posterPath)
        moviesDao.insertFavoriteMovies(favoriteMovie)
    }

    fun getFavoriteMovies(): Flow<List<FavoriteMovie>> =
        moviesDao.getAllFavoriteMovies()
}