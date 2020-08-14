package com.tengtonghann.android.movieum.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tengtonghann.android.movieum.model.Movie

/**
 * Database Migration
 */
object DatabaseMigrations {

    const val DB_VERSION = 1
    val MIGRATION: Array<Migration>
        get() = arrayOf(migration12())

    private fun migration12(): Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${Movie.TABLE_NAME} ADD COLUMN body TEXT")
        }
    }

}