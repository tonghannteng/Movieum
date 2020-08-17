package com.tengtonghann.android.movieum.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.FragmentFavoriteBinding
import com.tengtonghann.android.movieum.ui.base.BaseFragment
import com.tengtonghann.android.movieum.utils.Logger
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

    override fun getViewBinding(view: View): FragmentFavoriteBinding =
        FragmentFavoriteBinding.bind(view)

    override fun provideLayoutId(): Int = R.layout.fragment_favorite

    override fun setupView(view: View) {
        Logger.d(TAG, "setupView")
    }

    override fun initCreate() {
        Logger.d(TAG, "initCreate")
    }

}