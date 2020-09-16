package com.tengtonghann.android.movieum.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tengtonghann.android.movieum.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val _homeNavigationLiveData = MutableLiveData<Event<Boolean>>()
    private val _favoriteNavigationLiveData = MutableLiveData<Event<Boolean>>()
    private val _searchLiveData = MutableLiveData<Boolean>()

    val homeNavigationLiveData: LiveData<Event<Boolean>>
        get() = _homeNavigationLiveData

    val favoriteNavigationLiveData: LiveData<Event<Boolean>>
        get() = _favoriteNavigationLiveData

    val searchLiveData: LiveData<Boolean>
        get() = _searchLiveData

    fun onCreated() {
        _homeNavigationLiveData.postValue(Event(true))
    }

    fun onHomeSelected() {
        _homeNavigationLiveData.postValue(Event(true))
        _searchLiveData.postValue(true)
    }

    fun onFavoriteSelected() {
        _favoriteNavigationLiveData.postValue(Event(true))
        _searchLiveData.postValue(false)
    }

}