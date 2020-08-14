package com.tengtonghann.android.movieum.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tengtonghann.android.movieum.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO)
 * for [com.tengtonghann.android.movieum.data.db.MovieumDatabase]
 */
@Dao
interface PopularMovieDao {

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
}