package com.tengtonghann.android.movieum.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.FragmentFavoriteBinding
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.ui.base.BaseFragment
import com.tengtonghann.android.movieum.ui.detail.MovieDetailActivity
import com.tengtonghann.android.movieum.ui.favorite.adapter.FavoriteAdapter
import com.tengtonghann.android.movieum.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
        val imageViewPair = Pair.create<View, String>(imageView, getString(R.string.image_transition_name))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            mActivity,
            imageViewPair
        )
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.id)
        startActivity(intent, options.toBundle())
    }

    private fun onLikeClicked(movie: FavoriteMovie) {
        mViewModel.unlikeMovie(movie)
    }

    override fun getViewBinding(view: View): FragmentFavoriteBinding =
        FragmentFavoriteBinding.bind(view)

    override fun provideLayoutId(): Int = R.layout.fragment_favorite

    override fun setupView(view: View) {
        mActivity = activity as MainActivity
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