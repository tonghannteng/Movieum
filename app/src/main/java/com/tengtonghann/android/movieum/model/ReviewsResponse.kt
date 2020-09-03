package com.tengtonghann.android.movieum.model

import com.google.gson.annotations.SerializedName

data class ReviewsResponse (

    @SerializedName("results")
    var reviews: List<Review>? = null
)
