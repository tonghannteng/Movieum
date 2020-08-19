package com.tengtonghann.android.movieum.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.model.Movie
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
     * Updates favorite column from [Movie] to 1
     */
    @Query("UPDATE ${Movie.TABLE_NAME} SET is_favorite = 1 WHERE id = :movieId")
    suspend fun updateFavoriteMovie(movieId: Long)

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