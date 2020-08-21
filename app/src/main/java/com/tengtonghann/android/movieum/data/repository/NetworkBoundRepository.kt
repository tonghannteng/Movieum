package com.tengtonghann.android.movieum.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.tengtonghann.android.movieum.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * [RESULT] is type for database, data for UI display
 * [REQUEST] is type for request network, save
 *
 * Repository provides resource from database and remote end point.
 */
@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<RESULT, REQUEST> {

    /**
     * Get data from Network call -> Save data to database -> get data from database -> Display to UI
     */
    fun asFlow() = flow<State<RESULT>> {

        // Emit loading State
        emit(State.loading())

        try {
            // Emit database content first
            emit(State.success(fetchFromDatabase().first()))

            // fetch latest movies from remote
            val apiResponse = fetchMovieFromNetwork()

            // Parse body
            val moviesResponse = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && moviesResponse != null) {
                // Save movies into database
                saveNetworkData(moviesResponse)
            } else {
                // Something went wrong! Emit Error state
                emit(State.error(apiResponse.message()))
            }

        } catch (ex: Exception) {
            // Emit error! Exception occurred.
            emit(State.error("Network error! cannot get latest movies."))
            // TODO: Add Exception to Firebase
            ex.printStackTrace()
        }

        // Retrieve movies from database and emit
        emitAll(
            fetchFromDatabase().map {
                State.success(it)
            }
        )
    }.catch { ex ->
        emit(State.error("Server error! can't get the movie"))
        // TODO: Add Exception to Firebase
        ex.printStackTrace()
    }

    /**
     * Save network data to database
     */
    @WorkerThread
    protected abstract suspend fun saveNetworkData(response: REQUEST)

    /**
     * Retrieve all data from database
     */
    @MainThread
    protected abstract suspend fun fetchFromDatabase(): Flow<RESULT>

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchMovieFromNetwork(): Response<REQUEST>
}