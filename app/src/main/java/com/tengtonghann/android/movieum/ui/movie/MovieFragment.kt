package com.tengtonghann.android.movieum.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.databinding.FragmentMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.ui.detail.MovieDetailActivity
import com.tengtonghann.android.movieum.ui.main.MainActivity
import com.tengtonghann.android.movieum.ui.movie.adapter.PopularAdapter
import com.tengtonghann.android.movieum.ui.movie.adapter.TopRatedAdapter
import com.tengtonghann.android.movieum.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * @author Tonghann Teng
 */
@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    companion object {
        const val TAG = "MovieFragment"
        const val DEFAULT_PAGE = 1

        fun newInstance(): MovieFragment {
            val args = Bundle()
            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentMovieBinding

    /**
     * Inject ViewModel [MovieViewModel]
     */
    private val mViewModel: MovieViewModel by viewModels()
    private lateinit var mActivity: AppCompatActivity
    private val mMovieAdapter = PopularAdapter(this::onFavoriteClicked, this::onItemClicked)
    private val mTopRatedAdapter = TopRatedAdapter(this::onFavoriteClicked, this::onItemClicked)

    private fun onFavoriteClicked(movie: Movie) {
        mViewModel.onFavoriteMovie(movie)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        mActivity = activity as MainActivity

        // Initialize Popular RecyclerView
        binding.popularMovieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mMovieAdapter
        }

        // Initialize Top Rated RecyclerView
        binding.topRatedMovieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mTopRatedAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            getMovies()
        }

        initData()
    }

    private fun onItemClicked(movie: Movie, imageView: ImageView) {
        val intent = Intent(mActivity, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.id)
        intent.putExtra(MovieDetailActivity.SCREEN_FLAG, true)
        startActivity(intent)
    }

    private fun initData() {

        if ((mViewModel.popularMoviesLiveData.value !is State.Success) || (mViewModel.topRatedMoviesLiveData.value !is State.Success)) {
            getMovies()
        }

        mViewModel.popularMoviesLiveData.observe(viewLifecycleOwner) { state ->
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

        mViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) { state ->
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
    }

    private fun getMovies() {
        mViewModel.getPopularMovies(DEFAULT_PAGE)
        mViewModel.getTopRatedMovies(DEFAULT_PAGE)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }
}