package com.tengtonghann.android.movieum.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.databinding.ActivityMainBinding
import com.tengtonghann.android.movieum.model.State
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.main.adapter.MovieAdapter
import com.tengtonghann.android.movieum.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        const val TAG = "MainActivity"
    }

    override val mViewModel: MainViewModel by viewModels()

    private val mAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        // Initialize RecyclerView
        mViewBinding.moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = mAdapter
        }

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
                    is State.Loading -> {
                        Logger.d(TAG, "Loading")
                    }
                    is State.Success -> {
                        mAdapter.submitList(state.data)
                    }
                    is State.Error -> {
                        Logger.d(TAG, "Failed")
                    }
                }
            }
        )
    }

    private fun getMovies() {
        mViewModel.getMovies(1)
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}