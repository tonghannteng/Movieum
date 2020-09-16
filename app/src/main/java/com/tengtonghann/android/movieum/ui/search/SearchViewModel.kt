package com.tengtonghann.android.movieum.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengtonghann.android.movieum.data.repository.MoviesRepository
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.model.MoviesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _searchMoviesLiveData = MutableLiveData<State<MoviesResponse>>()

    val searchMoviesLiveData: LiveData<State<MoviesResponse>>
        get() = _searchMoviesLiveData

    fun getSearchMovies(queryText: String, page: Int) {
        viewModelScope.launch {
            moviesRepository.getSearchMovies(queryText, page).collect {
                _searchMoviesLiveData.value = it
            }
        }
    }

}