package com.tengtonghann.android.movieum.data.exception

open class ApiRequestException(genericUrl: String) : HttpException(genericUrl) {

    override fun fillInStackTrace(): Throwable {
        return this
    }
}