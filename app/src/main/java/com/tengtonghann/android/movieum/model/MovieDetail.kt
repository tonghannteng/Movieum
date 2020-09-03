package com.tengtonghann.android.movieum.model

import androidx.room.Embedded
import androidx.room.Relation
import java.util.ArrayList

data class MovieDetail(

    @Embedded
    var movie: Movie? = null,

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var trailers: List<Trailer> = ArrayList(),

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var castList: List<Cast> = ArrayList(),

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var reviews: List<Review> = ArrayList()
)