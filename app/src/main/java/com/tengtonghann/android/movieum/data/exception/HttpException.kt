package com.tengtonghann.android.movieum.data.exception

import java.io.IOException

open class HttpException : IOException {

    constructor() : super()
    constructor(message: String): super(message)
}