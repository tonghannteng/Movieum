package com.tengtonghann.android.movieum.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tengtonghann.android.movieum.model.Cast

@Dao
interface CastsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCasts(casts: List<Cast>)
}