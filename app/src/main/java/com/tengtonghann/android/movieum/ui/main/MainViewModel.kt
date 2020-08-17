package com.tengtonghann.android.movieum.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tengtonghann.android.movieum.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    val homeNavigation = MutableLiveData<Event<Boolean>>()
    val favoriteNavigation = MutableLiveData<Event<Boolean>>()

    fun onCreated() {
        homeNavigation.postValue(Event(true))
    }

    fun onHomeSelected() {
        homeNavigation.postValue(Event(true))
    }

    fun onFavoriteSelected() {
        favoriteNavigation.postValue(Event(true))
    }

}