package com.tengtonghann.android.movieum.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.databinding.ActivityMovieDetailBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.detail.cast.CastAdapter
import com.tengtonghann.android.movieum.ui.detail.review.ReviewAdapter
import com.tengtonghann.android.movieum.ui.detail.trailer.TrailerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.partial_details_info.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding>() {

    override val mViewModel: MovieDetailViewModel by viewModels()
    private lateinit var movie: Movie
    private val mCastAdapter = CastAdapter()
    private val mTrailerAdapter = TrailerAdapter(this::onTrailerClick)
    private val mReviewAdapter = ReviewAdapter()

    private fun onTrailerClick(trailerKey: String) {
        val appIntent = Intent(Intent(Intent.ACTION_VIEW, Uri.parse("${YOUTUBE_VND}${trailerKey}")))
        val webIntent = Intent(Intent(Intent.ACTION_VIEW, Uri.parse("${YOUTUBE_WEB_URL}${trailerKey}")))
        if (appIntent.resolveActivity(packageManager) != null) {
            startActivity(appIntent)
        } else {
            startActivity(webIntent)
        }
    }

    override fun getViewBinding(): ActivityMovieDetailBinding =
        ActivityMovieDetailBinding.inflate(layoutInflater)

    override fun setContentView() {
        setContentView(R.layout.activity_movie_detail)
    }

    override fun setupView() {
        // Set default title empty
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        handleCollapseToolbarTitle()

        // Cast RecyclerView
        castRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mCastAdapter
        }

        // Trailer RecyclerView
        trailerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mTrailerAdapter
        }

        // Review RecyclerView
        reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mReviewAdapter
        }
    }

    private fun getDetailMovies(id: Long) {
        mViewModel.getDetailMovies(id)
    }

    override fun setUpObservers() {
        val movieId = intent.extras?.getLong(MOVIE_ID)
            ?: throw IllegalArgumentException("movieId must be non-nul")

        if (mViewModel.detailMoviesLiveData.value !is State.Success) {
            getDetailMovies(movieId)
        }

        mViewModel.detailMoviesLiveData.observe(
            this,
            { state ->
                when (state) {
                    is State.Loading -> {
                        Log.d(TAG, "Loading")
                        // TODO: Add Loading
                    }
                    is State.Success -> {
                        this@MovieDetailActivity.movie = state.data.movie!!
                        initDataToView(movie)
                        mCastAdapter.submitList(state.data.castList)
                        mTrailerAdapter.submitList(state.data.trailers)
                        label_reviews.visibility =
                            if (state.data.reviews.isEmpty()) View.GONE else View.VISIBLE
                        if (state.data.reviews.isNotEmpty()) {
                            mReviewAdapter.submitList(state.data.reviews)
                        }
                    }
                    is State.Error -> {
                        Log.d(TAG, "Error")
                        // TODO: add Firebase Crashlytics
                    }
                }
            }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun initDataToView(movie: Movie) {
        text_title.text = movie.title
        text_release_date.text = movie.releaseDate
        label_vote.text = "${movie.voteCount} votes"
        text_vote.text = movie.voteAverage.toString()
        text_language.text = movie.originalLanguage
        text_overview.text = movie.overview
        val movieImage = IMAGE_BASE_URL + IMAGE_SIZE_W780 + movie.backdropPath

        Glide.with(applicationContext)
            .load(movieImage)
            .into(imageMovieBackdrop)

        Glide.with(applicationContext)
            .load(movieImage)
            .into(imagePoster)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        supportFinishAfterTransition()
        return super.onOptionsItemSelected(item)
    }

    /**
     * Set the title on the toolbar only if the toolbar is collapsed
     */
    private fun handleCollapseToolbarTitle() {
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    if (appBarLayout != null) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = movie.title
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = ""
                    isShow = false
                }
            }

        })
    }

    companion object {
        const val MOVIE_ID = "movieId"
        const val TAG = "MovieDetailActivity"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W780 = "w780"
        const val YOUTUBE_VND = "vnd.youtube:"
        const val YOUTUBE_WEB_URL = "https://www.youtube.com/watch?v="
    }
}