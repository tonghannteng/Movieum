package com.tengtonghann.android.movieum.ui.main

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ActivityMainBinding
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.favorite.FavoriteFragment
import com.tengtonghann.android.movieum.ui.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        const val TAG = "MainActivity"
    }

    /**
     * Inject ViewModel [MainViewModel]
     */
    override val mViewModel: MainViewModel by viewModels()

    private var activeFragment: Fragment? = null

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupView() {
        mViewBinding.bottomNavigation.run {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemHome -> {
                        mViewModel.onHomeSelected()
                        true
                    }
                    R.id.itemFavoriteMovie -> {
                        mViewModel.onFavoriteSelected()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showHome() {
        if (activeFragment is MovieFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(MovieFragment.TAG) as MovieFragment?

        if (fragment == null) {
            fragment = MovieFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, MovieFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)
        fragmentTransaction.commit()
        activeFragment = fragment
    }

    private fun showFavorite() {
        if (activeFragment is FavoriteFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(FavoriteFragment.TAG) as FavoriteFragment?

        if (fragment == null) {
            fragment = FavoriteFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, FavoriteFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)
        fragmentTransaction.commit()
        activeFragment = fragment
    }

    override fun setContentView() {
        mViewModel.onCreated()
        setContentView(mViewBinding.root)
    }

    override fun setUpObservers() {
        mViewModel.homeNavigationLiveData.observe(this, Observer {
            it.getIfNotHandled()?.run { showHome() }
        })
        mViewModel.favoriteNavigationLiveData.observe(this, Observer {
            it.getIfNotHandled()?.run { showFavorite() }
        })
    }

}