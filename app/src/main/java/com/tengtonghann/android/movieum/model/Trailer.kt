package com.tengtonghann.android.movieum.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "trailer",
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["movie_id"])]
)
data class Trailer(

    @PrimaryKey
    @SerializedName("id")
    var id: String,

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,

    @SerializedName("key")
    var key: String? = null,

    @SerializedName("site")
    var site: String? = null,

    @SerializedName("name")
    var title: String? = null
)