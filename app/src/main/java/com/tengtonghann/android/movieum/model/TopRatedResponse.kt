package com.tengtonghann.android.movieum.model

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(

    @SerializedName("page")
    var page: Int = 0,

    @SerializedName("total_results")
    var totalResults: Int = 0,

    @SerializedName("total_pages")
    var totalPages: Int = 0,

    @SerializedName("results")
    var movies: List<TopRatedMovie>? = null
)