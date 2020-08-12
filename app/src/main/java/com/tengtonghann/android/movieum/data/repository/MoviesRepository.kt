package com.tengtonghann.android.movieum.data.repository

import com.tengtonghann.android.movieum.data.remote.MovieumService
import com.tengtonghann.android.movieum.model.MoviesResponse
import com.tengtonghann.android.movieum.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesRepository @Inject constructor(
    private val movieumService: MovieumService
) {

    fun getAllMovies(page: Int): Flow<State<MoviesResponse>> {

        return object : NetworkBoundRepository<MoviesResponse>() {

            override suspend fun fetchMovieFromRemote(): Response<MoviesResponse> =
                movieumService.getPopularMovie(page)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}