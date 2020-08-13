package com.tengtonghann.android.movieum.di

import com.tengtonghann.android.movieum.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.MOVIEUM_API_KEY)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}