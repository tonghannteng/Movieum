package com.tengtonghann.android.movieum.data.repository

import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.MovieDetail
import com.tengtonghann.android.movieum.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepositoryInterface {

    fun getPopularMovies(page: Int): Flow<State<List<Movie>>>

    fun getTopRatedMovies(page: Int): Flow<State<List<Movie>>>

    fun getSearchMovies(query: String, page: Int): Flow<State<MoviesResponse>>

    fun getMovieDetail(id: Long): Flow<State<MovieDetail>>

    fun getSearchMovieDetail(id: Long): Flow<State<Movie>>

    suspend fun addFavoriteMovie(movie: Movie)

    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>

    fun saveDetailMovie(movie: Movie)

    suspend fun unlikeMovie(movie: FavoriteMovie)

}