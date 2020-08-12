package com.tengtonghann.android.movieum.model

/**
 * State Management for UI and Data
 */
sealed class State<T> {

    class Loading<T> : State<T>()

    data class Success<T>(val data: T) : State<T>()

    data class Error<T>(val message: String) : State<T>()

    companion object {
        /**
         * Returns [State.Loading]
         */
        fun <T> loading() =
            Loading<T>()

        /**
         * Returns [State.Success]
         */
        fun <T> success(data: T) =
            Success(data)

        /**
         * Returns [State.Error]
         */
        fun <T> error(message: String) =
            Error<T>(message)
    }
}