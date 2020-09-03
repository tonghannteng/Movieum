package com.tengtonghann.android.movieum.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tengtonghann.android.movieum.model.Trailer

@Dao
interface TrailersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrailers(trailers: List<Trailer>)
}