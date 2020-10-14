package com.tengtonghann.android.movieum.data.exception

class PageNotFoundException : HttpException() {

    override val message: String
        get() {
            return "404 page not found exception"
        }
}