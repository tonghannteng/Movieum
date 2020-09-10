package com.tengtonghann.android.movieum.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.MoviesRepository
import com.tengtonghann.android.movieum.model.FavoriteMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@ExperimentalCoroutinesApi
class FavoriteViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _favoriteMoviesLiveData = MutableLiveData<List<FavoriteMovie>>()
    private val _showEmptyText = MutableLiveData<Boolean>()

    val favoriteMoviesLiveData: LiveData<List<FavoriteMovie>>
        get() = _favoriteMoviesLiveData

    val showEmptyText: LiveData<Boolean>
        get() = _showEmptyText

    fun getFavoriteMovies() {
        viewModelScope.launch {
            moviesRepository.getFavoriteMovies().collect {
                _favoriteMoviesLiveData.value = it
                _showEmptyText.value = it.isNotEmpty()
            }
        }
    }

    fun unlikeMovie(movie: FavoriteMovie) {
        viewModelScope.launch {
            moviesRepository.unlikeMovie(movie)
        }
    }
}