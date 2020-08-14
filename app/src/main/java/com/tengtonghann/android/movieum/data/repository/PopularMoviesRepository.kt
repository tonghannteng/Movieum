package com.tengtonghann.android.movieum.data.repository

import com.tengtonghann.android.movieum.data.dao.PopularMovieDao
import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.MoviesResponse
import com.tengtonghann.android.movieum.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [PopularMoviesRepository] is a Singleton class for fetching data from network and
 * storing in database for offline.
 */

@ExperimentalCoroutinesApi
@Singleton
class PopularMoviesRepository @Inject constructor(
    private val movieumService: MovieumService,
    private val popularMovieDao: PopularMovieDao
) {

    /**
     * Fetched data from network and stored in database.
     * data from persistence is fetched and emitted.
     */
    fun getAllMovies(page: Int): Flow<State<List<Movie>>> {

        return object : NetworkBoundRepository<List<Movie>, MoviesResponse>() {

            override suspend fun saveNetworkData(response: MoviesResponse) {
                response.movies?.let { popularMovieDao.insertPopularMovies(it) }
            }

            override suspend fun fetchFromDatabase(): Flow<List<Movie>> =
                popularMovieDao.getAllPopularMovies()

            override suspend fun fetchMovieFromNetwork(): Response<MoviesResponse> =
                movieumService.getPopularMovie(page)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}