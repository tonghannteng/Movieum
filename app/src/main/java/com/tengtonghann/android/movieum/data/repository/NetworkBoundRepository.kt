package com.tengtonghann.android.movieum.data.repository

import android.util.Log
import androidx.annotation.MainThread
import com.tengtonghann.android.movieum.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<RESULT> {

    fun asFlow() = flow<State<RESULT>> {

        // Emit loading State
        emit(State.loading())

        try {

            // fetch latest movies from remote
            val apiResponse = fetchMovieFromRemote()

            // Parse body
            val remotePosts = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && remotePosts != null) {
                emit(State.success(remotePosts))
            } else {
                emit(State.error(apiResponse.message()))
            }

        } catch (ex: Exception) {
            emit(State.error("Network error! cannot get latest movies."))
            ex.printStackTrace()
        }
    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchMovieFromRemote(): Response<RESULT>
}