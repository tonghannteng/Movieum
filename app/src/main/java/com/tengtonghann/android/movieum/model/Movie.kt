package com.tengtonghann.android.movieum.model

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Movie.TABLE_NAME)
data class Movie (

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

    @SerializedName("genres")
    var genres: List<Int>? = null,

    @SerializedName("videos")
    var trailersResponse: Boolean,

    @SerializedName("original_language")
    var originalLanguage: String? = null
) {
    companion object {
        const val TABLE_NAME = "movieum_movie"
    }
}
