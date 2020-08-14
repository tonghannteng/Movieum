package com.tengtonghann.android.movieum.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.PopularMoviesRepository
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val moviesRepository: PopularMoviesRepository) :
    ViewModel() {

    private val _moviesLiveData = MutableLiveData<State<List<Movie>>>()

    val moviesLiveData: LiveData<State<List<Movie>>>
        get() = _moviesLiveData

    fun getMovies(page: Int) {
        viewModelScope.launch {

            moviesRepository.getAllMovies(page).collect {
                _moviesLiveData.value = it
            }

        }
    }
}