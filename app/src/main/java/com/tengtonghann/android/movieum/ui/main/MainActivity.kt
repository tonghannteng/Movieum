package com.tengtonghann.android.movieum.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.databinding.ActivityMainBinding
import com.tengtonghann.android.movieum.model.State
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.main.adapter.MovieAdapter
import com.tengtonghann.android.movieum.ui.main.adapter.TopRatedMovieAdapter
import com.tengtonghann.android.movieum.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        const val TAG = "MainActivity"
    }

    override val mViewModel: MainViewModel by viewModels()

    private val mPopularMovieAdapter = MovieAdapter()
    private val mTopRatedMovieAdapter = TopRatedMovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        // Initialize Popular RecyclerView
        mViewBinding.popularMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = mPopularMovieAdapter
        }

        // Initialize Trending RecyclerView
        mViewBinding.trendingMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = mTopRatedMovieAdapter
        }
        initMovies()
    }

    private fun initMovies() {
        if ((mViewModel.popularMoviesLiveData.value !is State.Success) || (mViewModel.topRatedMoviesLiveData.value !is State.Success)) {
            getMovies()
        }

        mViewModel.topRatedMoviesLiveData.observe(
            this,
            Observer { state ->
                when (state) {
                    is State.Loading -> showLoading(true)
                    is State.Success -> {
                        mTopRatedMovieAdapter.submitList(state.data)
                        showLoading(false)
                    }
                    is State.Error -> {
                        showLoading(false)
                        Logger.d(TAG, "Failed")
                    }
                }

            }
        )

        mViewModel.popularMoviesLiveData.observe(
            this,
            Observer { state ->
                when (state) {
                    is State.Loading -> showLoading(true)
                    is State.Success -> {
                        mPopularMovieAdapter.submitList(state.data)
                        showLoading(false)
                    }
                    is State.Error -> {
                        showLoading(false)
                        Logger.d(TAG, "Failed")
                    }
                }
            }
        )
    }

    private fun getMovies() {
        mViewModel.getMovies(1)
        mViewModel.getTopRatedMovies(1)
    }

    private fun showLoading(isLoading: Boolean) {
        mainProgressBar.isVisible = isLoading
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}