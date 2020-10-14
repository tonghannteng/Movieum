package com.tengtonghann.android.movieum.data.exception

import java.io.IOException

class AuthenticationException : IOException() {

    override val message: String
        get() {
            return "Authentication Exception"
        }

}