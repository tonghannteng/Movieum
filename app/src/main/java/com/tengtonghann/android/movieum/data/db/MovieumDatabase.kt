package com.tengtonghann.android.movieum.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tengtonghann.android.movieum.data.dao.PopularMovieDao
import com.tengtonghann.android.movieum.model.Movie

/**
 * Abstract [MovieumDatabase]. It provides DAO [PopularMovieDao] by using [getPopularMovieDao]
 */
@Database(
    entities = [Movie::class],
    version = DatabaseMigrations.DB_VERSION
)
abstract class MovieumDatabase : RoomDatabase() {

    /**
     * @return [PopularMovieDao] Movieum Movie Data Access Object
     */
    abstract fun getPopularMovieDao(): PopularMovieDao

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