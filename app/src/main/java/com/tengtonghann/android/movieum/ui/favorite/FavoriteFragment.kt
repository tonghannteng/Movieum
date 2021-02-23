package com.tengtonghann.android.movieum.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.FragmentFavoriteBinding
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.ui.base.BaseFragment
import com.tengtonghann.android.movieum.ui.detail.MovieDetailActivity
import com.tengtonghann.android.movieum.ui.favorite.adapter.FavoriteAdapter
import com.tengtonghann.android.movieum.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>() {

    companion object {
        const val TAG = "FavoriteFragment"

        fun newInstance(): FavoriteFragment {
            val args = Bundle()
            val fragment = FavoriteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Inject ViewModel [FavoriteViewModel]
     */
    override val mViewModel: FavoriteViewModel by viewModels()
    private lateinit var mActivity: AppCompatActivity

    private val mFavoriteAdapter =
        FavoriteAdapter(this::onItemClicked, this::onLikeClicked)

    private fun onItemClicked(movie: FavoriteMovie, imageView: ImageView) {
        val intent = Intent(mActivity, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.id)
        intent.putExtra(MovieDetailActivity.SCREEN_FLAG, true)
        startActivity(intent)
    }

    private fun onLikeClicked(movie: FavoriteMovie) {
        mViewModel.unlikeMovie(movie)
    }

    override fun getViewBinding(view: View): FragmentFavoriteBinding =
        FragmentFavoriteBinding.bind(view)

    override fun provideLayoutId(): Int = R.layout.fragment_favorite

    override fun setupView(view: View) {
        mActivity = activity as MainActivity
        MobileAds.initialize(context) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        mViewBinding.favoriteMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mFavoriteAdapter
        }
    }

    override fun initData() {
        mViewModel.getFavoriteMovies()
        mViewModel.favoriteMoviesLiveData.observe(
            this,
            Observer {
                mFavoriteAdapter.submitList(it)
            })

        mViewModel.showEmptyText.observe(
            this,
            Observer {
                mViewBinding.emptyText.visibility = if (it) View.GONE else View.VISIBLE
            }
        )
    }
}