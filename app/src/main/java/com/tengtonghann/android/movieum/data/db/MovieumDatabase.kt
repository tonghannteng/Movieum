package com.tengtonghann.android.movieum.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tengtonghann.android.movieum.data.dao.CastsDao
import com.tengtonghann.android.movieum.data.dao.MoviesDao
import com.tengtonghann.android.movieum.data.dao.ReviewsDao
import com.tengtonghann.android.movieum.data.dao.TrailersDao
import com.tengtonghann.android.movieum.model.*

/**
 * Abstract [MovieumDatabase]. It provides DAO [MoviesDao] by using [getPopularMovieDao]
 */
@Database(
    entities = [Movie::class, FavoriteMovie::class, Trailer::class, Cast::class, Review::class],
    version = DatabaseMigrations.DB_VERSION,
    exportSchema = false
)
abstract class MovieumDatabase : RoomDatabase() {

    /**
     * @return [MoviesDao] Movieum Movie Data Access Object
     */
    abstract fun getMoviesMovieDao(): MoviesDao
    abstract fun getCastsDao(): CastsDao
    abstract fun getReviewsDao(): ReviewsDao
    abstract fun getTrailersDao(): TrailersDao

    companion object {
        const val DB_NAME = "movieum_database"

        @Volatile
        private var INSTANCE: MovieumDatabase? = null

        fun getInstance(context: Context): MovieumDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieumDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATION)
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

}