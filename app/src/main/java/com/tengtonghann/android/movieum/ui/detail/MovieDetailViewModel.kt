package com.tengtonghann.android.movieum.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.MoviesRepository
import com.tengtonghann.android.movieum.model.MovieDetail
import com.tengtonghann.android.movieum.data.state.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieDetailViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _detailMoviesLiveData = MutableLiveData<State<MovieDetail>>()

    val detailMoviesLiveData: LiveData<State<MovieDetail>>
        get() = _detailMoviesLiveData

    fun getDetailMovies(id: Long) {
        viewModelScope.launch {
            moviesRepository.getMovieDetail(id).collect {
                _detailMoviesLiveData.value = it
            }
        }
    }
}