package com.tengtonghann.android.movieum.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.FragmentFavoriteBinding
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.ui.base.BaseFragment
import com.tengtonghann.android.movieum.ui.favorite.adapter.FavoriteAdapter
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
    private val mFavoriteAdapter =
        FavoriteAdapter(this::onItemClicked)

    private fun onItemClicked(movie: FavoriteMovie) {
        Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
    }

    override fun getViewBinding(view: View): FragmentFavoriteBinding =
        FragmentFavoriteBinding.bind(view)

    override fun provideLayoutId(): Int = R.layout.fragment_favorite

    override fun setupView(view: View) {
        mViewBinding.favoriteMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mFavoriteAdapter
        }
    }

    override fun initCreate() {
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