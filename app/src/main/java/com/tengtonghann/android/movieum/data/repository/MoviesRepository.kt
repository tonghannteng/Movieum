package com.tengtonghann.android.movieum.data.repository

import com.tengtonghann.android.movieum.data.dao.CastsDao
import com.tengtonghann.android.movieum.data.dao.MoviesDao
import com.tengtonghann.android.movieum.data.dao.ReviewsDao
import com.tengtonghann.android.movieum.data.dao.TrailersDao
import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.data.state.State
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
    private val moviesDao: MoviesDao,
    private val castsDao: CastsDao,
    private val reviewsDao: ReviewsDao,
    private val trailersDao: TrailersDao
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

    fun getMovieDetail(id: Long): Flow<State<MovieDetail>> {
        return object : NetworkBoundRepository<MovieDetail, Movie>() {
            override suspend fun saveNetworkData(response: Movie) {
                saveDetailMovie(response)
            }

            override suspend fun fetchFromDatabase(): Flow<MovieDetail> =
                moviesDao.getMovieDetail(id)

            override suspend fun fetchMovieFromNetwork(): Response<Movie> =
                movieumService.getMovieDetail(id)

        }.asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun addFavoriteMovie(movie: Movie) {
        /**
         * TODO: Find a way to convert [Movie] and [FavoriteMovie] object, both have the same object
         * Current implement set [Movie] properties to [FavoriteMovie]
         */
        val favoriteMovie = FavoriteMovie(movie.id, movie.title, movie.posterPath, movie.overview)
        moviesDao.insertFavoriteMovies(favoriteMovie)
    }

    fun getFavoriteMovies(): Flow<List<FavoriteMovie>> =
        moviesDao.getAllFavoriteMovies()

    fun saveDetailMovie(movie: Movie) {
        movie.reviewsResponse?.let {
            it.reviews?.let { review ->
                insertReview(review, movie.id)
            }
        }
        movie.creditsResponse?.let {
            it.cast?.let { cast ->
                insertCast(cast, movie.id)
            }
        }
        movie.trailersResponse?.let {
            it.trailers?.let { trailer ->
                insertTrailer(trailer, movie.id)
            }
        }
    }

    private fun insertReview(reviews: List<Review>, id: Long) {
        for (review in reviews) {
            review.movieId = id
        }
        reviewsDao.insertReviews(reviews)
    }

    private fun insertCast(casts: List<Cast>, id: Long) {
        for (cast in casts) {
            cast.movieId = id
        }
        castsDao.insertCasts(casts)
    }

    private fun insertTrailer(trailers: List<Trailer>, id: Long) {
        for (trailer in trailers) {
            trailer.movieId = id
        }
        trailersDao.insertTrailers(trailers)
    }
}