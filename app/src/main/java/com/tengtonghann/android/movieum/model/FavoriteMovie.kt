package com.tengtonghann.android.movieum.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tengtonghann.android.movieum.model.FavoriteMovie.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class FavoriteMovie(

    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("overview")
    var overview: String? = null
) {
    companion object {
        const val TABLE_NAME = "favorite_movie"
    }
}
