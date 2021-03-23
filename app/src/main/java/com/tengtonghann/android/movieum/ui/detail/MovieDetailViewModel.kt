package com.tengtonghann.android.movieum.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.MoviesRepositoryInterface
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.MovieDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieDetailViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepositoryInterface
) : ViewModel() {

    private val _detailMoviesLiveData = MutableLiveData<State<MovieDetail>>()
    private val _detailSearchMoviesLiveData = MutableLiveData<State<Movie>>()

    val detailMoviesLiveData: LiveData<State<MovieDetail>>
        get() = _detailMoviesLiveData

    val detailSearchMovieLiveData: LiveData<State<Movie>>
        get() = _detailSearchMoviesLiveData

    fun getDetailMovies(id: Long) {
        viewModelScope.launch {
            moviesRepository.getMovieDetail(id).collect {
                _detailMoviesLiveData.value = it
            }
        }
    }

    fun getSearchDetailMovies(id: Long) {
        viewModelScope.launch {
            moviesRepository.getSearchMovieDetail(id).collect {
                _detailSearchMoviesLiveData.value = it
            }
        }
    }
}