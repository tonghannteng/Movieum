package com.tengtonghann.android.movieum.ui.movie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.FragmentMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.State
import com.tengtonghann.android.movieum.ui.base.BaseFragment
import com.tengtonghann.android.movieum.ui.main.adapter.MovieAdapter
import com.tengtonghann.android.movieum.ui.main.adapter.TopRatedMovieAdapter
import com.tengtonghann.android.movieum.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
    private val mPopularMovieAdapter = MovieAdapter(this::onItemClicked)

    private fun onItemClicked(movie: Movie) {
        mViewModel.onFavoriteMovie(movie)
        Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
    }

    private val mTopRatedMovieAdapter = TopRatedMovieAdapter()

    override fun initCreate() {
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
        mViewModel.getMovies(FIRST_PAGE)
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
            adapter = mPopularMovieAdapter
        }

        // Initialize Trending RecyclerView
        mViewBinding.trendingMovieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mTopRatedMovieAdapter
        }
    }

    override fun getViewBinding(view: View): FragmentMovieBinding = FragmentMovieBinding.bind(view)

}