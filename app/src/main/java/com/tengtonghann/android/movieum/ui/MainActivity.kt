package com.tengtonghann.android.movieum.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.model.State
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMovies()
    }

    private fun initMovies() {
        if (mViewModel.moviesLiveData.value !is State.Success) {
            getMovies()
        }

        mViewModel.moviesLiveData.observe(
            this,
            Observer { state ->
                when (state) {
                    is State.Success -> {
                        Toast.makeText(this, state.data.totalPages.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("MainActivityLog", state.data.movies?.size.toString())
                    }
                    is State.Error -> {
                        Log.d("MainActivityLog", "Failed")
                    }
                }
            }
        )
    }

    private fun getMovies() {
        mViewModel.getMovies(1)
    }
}