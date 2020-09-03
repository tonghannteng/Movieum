package com.tengtonghann.android.movieum.model

import com.google.gson.annotations.SerializedName

data class CreditsResponse (

    @SerializedName("cast")
    var cast: List<Cast>? = null
)