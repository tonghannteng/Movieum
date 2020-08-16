package com.tengtonghann.android.movieum.ui.main

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
class MainViewModel: ViewModel() {

}