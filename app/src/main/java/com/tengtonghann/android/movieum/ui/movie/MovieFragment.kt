package com.tengtonghann.android.movieum.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.FragmentMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.ui.base.BaseFragment
import com.tengtonghann.android.movieum.ui.detail.MovieDetailActivity
import com.tengtonghann.android.movieum.ui.movie.adapter.PopularAdapter
import com.tengtonghann.android.movieum.ui.movie.adapter.TopRatedAdapter
import com.tengtonghann.android.movieum.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @author Tonghann Teng
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : BaseFragment<MovieViewModel, FragmentMovieBinding>() {

    companion object {
        const val TAG = "MovieFragment"
        const val FIRST_PAGE = 1

        fun newInstance(): MovieFragment {
            val args = Bundle()
            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Inject ViewModel [MovieViewModel]
     */
    override val mViewModel: MovieViewModel by viewModels()
    private val mMovieAdapter = PopularAdapter(this::onFavoriteClicked, this::onItemClicked)
    private val mTopRatedAdapter = TopRatedAdapter(this::onFavoriteClicked, this::onItemClicked)

    private fun onFavoriteClicked(movie: Movie) {
        mViewModel.onFavoriteMovie(movie)
    }

    private fun onItemClicked(movie: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.id)
        startActivity(intent)
    }

    override fun initCreate() {
        initMovies()
    }

    private fun initMovies() {
        if ((mViewModel.popularMoviesLiveData.value !is State.Success) || (mViewModel.topRatedMoviesLiveData.value !is State.Success)) {
            getMovies()
        }

        mViewModel.popularMoviesLiveData.observe(
            this,
            Observer { state ->
                when (state) {
                    is State.Loading -> showLoading(true)
                    is State.Success -> {
                        mMovieAdapter.submitList(state.data)
                        showLoading(false)
                    }
                    is State.Error -> {
                        showLoading(false)
                        Logger.d(TAG, "Error State getting popular movies")
                    }
                }
            }
        )

        mViewModel.topRatedMoviesLiveData.observe(
            this,
            Observer { state ->
                when (state) {
                    is State.Loading -> showLoading(true)
                    is State.Success -> {
                        mTopRatedAdapter.submitList(state.data)
                        showLoading(false)
                    }
                    is State.Error -> {
                        showLoading(false)
                        Logger.d(TAG, "Error State getting top rated movies")
                    }
                }

            }
        )
    }

    private fun getMovies() {
        mViewModel.getPopularMovies(FIRST_PAGE)
        mViewModel.getTopRatedMovies(FIRST_PAGE)
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.mainProgressBar.isVisible = isLoading
    }

    /**
     * Get [MovieFragment] layout
     */
    override fun provideLayoutId(): Int = R.layout.fragment_movie

    override fun setupView(view: View) {

        // Initialize Popular RecyclerView
        mViewBinding.popularMovieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mMovieAdapter
        }

        // Initialize Top Rated RecyclerView
        mViewBinding.topRatedMovieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mTopRatedAdapter
        }
    }

    // Binds [MovieFragment] view
    override fun getViewBinding(view: View): FragmentMovieBinding = FragmentMovieBinding.bind(view)

}