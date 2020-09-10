package com.tengtonghann.android.movieum.data.dao

import androidx.room.*
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.MovieDetail
import kotlinx.coroutines.flow.Flow

/**
 * @author Tonghann Teng
 * Data Access Object (DAO)
 * for [com.tengtonghann.android.movieum.data.db.MovieumDatabase]
 */
@Dao
interface MoviesDao {

    /**
     * Inserts popular [Movie] to database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(moviesResponse: List<Movie>)

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE id = :movieId")
    fun getMovieDetail(movieId: Long): Flow<MovieDetail>

    /**
     * Updates popular column from [Movie] to 1
     */
    @Query("UPDATE ${Movie.TABLE_NAME} SET is_popular = 1 WHERE id = :movieId")
    suspend fun updatePopularMovie(movieId: Long)

    /**
     * Updates top rated column from [Movie] to 1
     */
    @Query("UPDATE ${Movie.TABLE_NAME} SET is_top_rated = 1 WHERE id = :movieId")
    suspend fun updateTopRatedMovie(movieId: Long)

    /**
     * Delete unlike movie
     */
    @Delete
    suspend fun unlikeMovie(movie: FavoriteMovie)

    /**
     * Insert favorite movies [FavoriteMovie] to database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovies(favoriteMovie: FavoriteMovie)

    /**
     * Fetched all the popular movies from the [Movie.TABLE_NAME] table
     */
    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE is_popular = 1")
    fun getAllPopularMovies(): Flow<List<Movie>>

    /**
     * Fetched all top rated movies from [TopRatedMovie.TABLE_NAME] table
     */
    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE is_top_rated = 1")
    fun getAllTopRatedMovies(): Flow<List<Movie>>

    /**
     * Fetched all favorite movies from [FavoriteMovie.TABLE_NAME] table
     */
    @Query("SELECT * FROM ${FavoriteMovie.TABLE_NAME}")
    fun getAllFavoriteMovies(): Flow<List<FavoriteMovie>>

}