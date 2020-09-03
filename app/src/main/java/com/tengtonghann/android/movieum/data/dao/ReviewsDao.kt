package com.tengtonghann.android.movieum.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tengtonghann.android.movieum.model.Review

@Dao
interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertReviews(reviews: List<Review>)
}