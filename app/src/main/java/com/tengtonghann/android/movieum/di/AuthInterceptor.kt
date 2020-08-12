package com.tengtonghann.android.movieum.di

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "6311677ef041038470aae345cd71bb78"
    }
}