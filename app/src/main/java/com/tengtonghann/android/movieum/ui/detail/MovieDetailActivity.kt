package com.tengtonghann.android.movieum.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.databinding.ActivityMovieDetailBinding
import com.tengtonghann.android.movieum.model.Cast
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.Review
import com.tengtonghann.android.movieum.model.Trailer
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.detail.cast.CastAdapter
import com.tengtonghann.android.movieum.ui.detail.review.ReviewAdapter
import com.tengtonghann.android.movieum.ui.detail.trailer.TrailerAdapter
import com.tengtonghann.android.movieum.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<MovieDetailViewModel>() {

    override val mViewModel: MovieDetailViewModel by viewModels()
    private lateinit var movie: Movie
    private lateinit var trailers: List<Trailer>
    private lateinit var casts: List<Cast>
    private lateinit var reviews: List<Review>
    private val mCastAdapter = CastAdapter()
    private val mTrailerAdapter = TrailerAdapter(this::onTrailerClick)
    private val mReviewAdapter = ReviewAdapter()
    private lateinit var mBinding: ActivityMovieDetailBinding

    private fun onTrailerClick(trailerKey: String) {
        val appIntent = Intent(Intent(Intent.ACTION_VIEW, Uri.parse("${YOUTUBE_VND}${trailerKey}")))
        val webIntent =
            Intent(Intent(Intent.ACTION_VIEW, Uri.parse("${YOUTUBE_WEB_URL}${trailerKey}")))
        if (appIntent.resolveActivity(packageManager) != null) {
            startActivity(appIntent)
        } else {
            startActivity(webIntent)
        }
    }

    override fun setContentView() {
        mBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun setupView() {
        // Set default title empty
        mBinding.toolbar.title = ""
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        handleCollapseToolbarTitle()

        // Cast RecyclerView
        mBinding.movieDetailsInfo.castRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mCastAdapter
        }

        // Trailer RecyclerView
        mBinding.movieDetailsInfo.trailerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mTrailerAdapter
        }

        // Review RecyclerView
        mBinding.movieDetailsInfo.reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mReviewAdapter
        }
    }

    private fun getDetailMoviesFromHome(id: Long) =
        mViewModel.getDetailMovies(id)

    private fun getDetailMoviesFromSearch(id: Long) =
        mViewModel.getSearchDetailMovies(id)

    override fun setUpObservers() {
        val movieId = intent.extras?.getLong(MOVIE_ID)
            ?: throw IllegalArgumentException("movieId must be non-null")

        val screenFlag = intent.extras?.getBoolean(SCREEN_FLAG)
            ?: throw IllegalAccessException("movie screen must be non-null")

        if (screenFlag) {
            if (mViewModel.detailMoviesLiveData.value !is State.Success) {
                getDetailMoviesFromHome(movieId)
            }
        } else {
            if (mViewModel.detailSearchMovieLiveData.value !is State.Success) {
                getDetailMoviesFromSearch(movieId)
            }
        }

        mViewModel.detailMoviesLiveData.observe(this, Observer {
            when (it) {
                is State.Loading -> {
                    Log.d(TAG, "Loading")
                    // TODO: Add Loading
                }
                is State.Success -> {
                    val movieDetail = it.data
                    this@MovieDetailActivity.movie = movieDetail.movie!!
                    initDataToView(movie)

                    this@MovieDetailActivity.casts = movieDetail.castList
                    mBinding.movieDetailsInfo.labelCast.visibility =
                        if (casts.isEmpty()) GONE else VISIBLE
                    if (casts.isNotEmpty()) {
                        mCastAdapter.submitList(casts)
                    }

                    this@MovieDetailActivity.trailers = movieDetail.trailers
                    mBinding.movieDetailsInfo.labelTrailers.visibility =
                        if (trailers.isEmpty()) GONE else VISIBLE
                    if (trailers.isNotEmpty()) {
                        mTrailerAdapter.submitList(trailers)
                    }

                    this@MovieDetailActivity.reviews = movieDetail.reviews
                    mBinding.movieDetailsInfo.labelReviews.visibility =
                        if (reviews.isEmpty()) GONE else VISIBLE
                    if (reviews.isNotEmpty()) {
                        mReviewAdapter.submitList(reviews)
                    }
                }
                is State.Error -> {
                    Logger.d(TAG, "Error State getting movie detail")
                }
            }

        })

        mViewModel.detailSearchMovieLiveData.observe(
            this, Observer {
                when (it) {
                    is State.Loading -> {
                        Log.d(TAG, "Loading")
                        // TODO: Add Loading
                    }
                    is State.Success -> {
                        this@MovieDetailActivity.movie = it.data
                        initDataToView(movie)

                        this@MovieDetailActivity.casts = it.data.creditsResponse?.cast!!
                        mBinding.movieDetailsInfo.labelCast.visibility =
                            if (casts.isEmpty()) GONE else VISIBLE
                        if (casts.isNotEmpty()) {
                            mCastAdapter.submitList(casts)
                        }

                        this@MovieDetailActivity.trailers = it.data.trailersResponse?.trailers!!
                        mBinding.movieDetailsInfo.labelTrailers.visibility =
                            if (trailers.isEmpty()) GONE else VISIBLE
                        if (trailers.isNotEmpty()) {
                            mTrailerAdapter.submitList(trailers)
                        }

                        this@MovieDetailActivity.reviews = it.data.reviewsResponse?.reviews!!
                        mBinding.movieDetailsInfo.labelReviews.visibility =
                            if (reviews.isEmpty()) GONE else VISIBLE
                        if (reviews.isNotEmpty()) {
                            mReviewAdapter.submitList(it.data.reviewsResponse?.reviews)
                        }
                    }
                    is State.Error -> {
                        Logger.d(TAG, "Error State getting movie detail")
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun initDataToView(movie: Movie) {
        mBinding.movieDetailsInfo.textTitle.text = movie.title
        mBinding.movieDetailsInfo.textReleaseDate.text = movie.releaseDate
        mBinding.movieDetailsInfo.labelVote.text = "${movie.voteCount} votes"
        mBinding.movieDetailsInfo.textVote.text = movie.voteAverage.toString()
        mBinding.movieDetailsInfo.textLanguage.text = movie.originalLanguage
        mBinding.movieDetailsInfo.textOverview.text = movie.overview
        val movieBackdropImage = IMAGE_BASE_URL + IMAGE_SIZE_W780 + movie.backdropPath
        val moviePosterImage = IMAGE_BASE_URL + IMAGE_SIZE_W780 + movie.posterPath

        Glide.with(applicationContext)
            .load(movieBackdropImage)
            .into(mBinding.imageMovieBackdrop)

        Glide.with(applicationContext)
            .load(moviePosterImage)
            .into(mBinding.movieDetailsInfo.imagePoster)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Set the title on the toolbar only if the toolbar is collapsed
     */
    private fun handleCollapseToolbarTitle() {
        mBinding.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    if (appBarLayout != null) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                }
                if (scrollRange + verticalOffset == 0) {
                    if (::movie.isInitialized) {
                        mBinding.collapsingToolbar.title = movie.title
                    }
                    isShow = true
                } else if (isShow) {
                    mBinding.collapsingToolbar.title = ""
                    isShow = false
                }
            }

        })
    }

    companion object {
        const val MOVIE_ID = "movieId"
        const val SCREEN_FLAG = "screenFlag"
        const val TAG = "MovieDetailActivity"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W780 = "w780"
        const val YOUTUBE_VND = "vnd.youtube:"
        const val YOUTUBE_WEB_URL = "https://www.youtube.com/watch?v="
    }
}