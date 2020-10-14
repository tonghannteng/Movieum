package com.tengtonghann.android.movieum.data.exception

class InternalServerErrorException : HttpException() {

    override val message: String
        get() {
            return "500 Internal server error"
        }
}