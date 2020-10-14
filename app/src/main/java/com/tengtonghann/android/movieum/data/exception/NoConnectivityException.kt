package com.tengtonghann.android.movieum.data.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String
        get() {
            return "No connectivity exception"
        }
}