package com.tengtonghann.android.movieum.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.TopRatedMovie
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO)
 * for [com.tengtonghann.android.movieum.data.db.MovieumDatabase]
 */
@Dao
interface MoviesDao {

    /**
     * Inserts popular [Movie] to database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovies(moviesResponse: List<Movie>)

    /**
     * Delete all popular movies from the [Movie.TABLE_NAME] table
     */
    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    fun deleteAllPopularMovies()

    /**
     * Fetched all the popular movies from the [Movie.TABLE_NAME] table
     */
    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAllPopularMovies(): Flow<List<Movie>>

    /**
     * Inserts top rated movie [TopRatedMovie] to database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRatedMovies(topRateResponse: List<TopRatedMovie>)

    /**
     * Deletes all top rated movies from [TopRatedMovie.TABLE_NAME] table
     */
    @Query("DELETE FROM ${TopRatedMovie.TABLE_NAME}")
    fun deleteAllTopRatedMovies()

    /**
     * Fetched all top rated movies from [TopRatedMovie.TABLE_NAME] table
     */
    @Query("SELECT * FROM ${TopRatedMovie.TABLE_NAME}")
    fun getAllTopRatedMovies(): Flow<List<TopRatedMovie>>
}