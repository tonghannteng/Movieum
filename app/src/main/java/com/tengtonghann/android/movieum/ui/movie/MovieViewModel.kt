package com.tengtonghann.android.movieum.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.MoviesRepository
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.State
import com.tengtonghann.android.movieum.model.TopRatedMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _popularMoviesLiveData = MutableLiveData<State<List<Movie>>>()
    private val _topRatedMoviesLiveData = MutableLiveData<State<List<TopRatedMovie>>>()

    val popularMoviesLiveData: LiveData<State<List<Movie>>>
        get() = _popularMoviesLiveData

    val topRatedMoviesLiveData: LiveData<State<List<TopRatedMovie>>>
        get() = _topRatedMoviesLiveData


    fun getMovies(page: Int) {
        viewModelScope.launch {
            moviesRepository.getPopularMovies(page).collect {
                _popularMoviesLiveData.value = it
            }
        }
    }

    fun getTopRatedMovies(page: Int) {
        viewModelScope.launch {
            moviesRepository.getTopRatedMovies(page).collect {
                _topRatedMoviesLiveData.value = it
            }
        }
    }
}