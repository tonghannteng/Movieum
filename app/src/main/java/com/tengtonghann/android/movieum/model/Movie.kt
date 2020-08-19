package com.tengtonghann.android.movieum.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tengtonghann.android.movieum.model.Movie.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Movie(

    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("popularity")
    var popularity: Double = 0.toDouble(),

    @SerializedName("vote_count")
    var voteCount: Int = 0,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("videos")
    var trailersResponse: Boolean,

    /**
     * Add Popular Column for popular movie
     */
    @ColumnInfo(name = "is_popular")
    var isPopular: Boolean = false,

    /**
     * Add Top Rated Column for Top Rated movie
     */
    @ColumnInfo(name = "is_top_rated")
    var isTopRated: Boolean = false,

    /**
     * Add Favorite Column for Favorite movie
     */
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,


    @SerializedName("original_language")
    var originalLanguage: String? = null
) {
    companion object {
        const val TABLE_NAME = "movie"
    }
}
