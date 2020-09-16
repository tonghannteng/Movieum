package com.tengtonghann.android.movieum.data.repository

import androidx.annotation.MainThread
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.utils.Logger
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class NetworkBoundWithoutSavingRepository<RESULT> {

    fun asFlow() = flow<State<RESULT>> {

        // emit loading data
        emit(State.loading())

        try {

            val apiResponse = fetchMoviesFromNetwork()
            val moviesResponse = apiResponse.body()

            if (apiResponse.isSuccessful && moviesResponse != null) {
                emit(State.success(moviesResponse))
            } else {
                emit(State.error(apiResponse.message()))
            }
        } catch (ex: Exception) {
            // Emit error
            Logger.e(ex)
            ex.message?.let {
                Logger.d("NetworkBoundWithoutSavingRepository", it)
            }
            emit(State.error("Network error! cannot get latest movies"))

        }
    }

    @MainThread
    protected abstract suspend fun fetchMoviesFromNetwork(): Response<RESULT>
}

