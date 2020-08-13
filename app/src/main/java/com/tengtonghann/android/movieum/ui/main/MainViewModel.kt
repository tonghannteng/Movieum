package com.tengtonghann.android.movieum.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.MoviesRepository
import com.tengtonghann.android.movieum.model.MoviesResponse
import com.tengtonghann.android.movieum.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _moviesLiveData = MutableLiveData<State<MoviesResponse>>()

    val moviesLiveData: LiveData<State<MoviesResponse>>
        get() = _moviesLiveData

    fun getMovies(page: Int) {
        viewModelScope.launch {
//            val movie = async { moviesRepository.getAllMovies(page) }
//            movie.await().collect {
//                _moviesLiveData.value = it
//            }

            moviesRepository.getAllMovies(page).collect {
//                _moviesLiveData.value = it
                _moviesLiveData.postValue(it)
            }

        }
    }
}