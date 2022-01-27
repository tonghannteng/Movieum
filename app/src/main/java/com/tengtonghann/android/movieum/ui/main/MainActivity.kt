package com.tengtonghann.android.movieum.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ActivityMainBinding
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.favorite.FavoriteFragment
import com.tengtonghann.android.movieum.ui.movie.MovieFragment
import com.tengtonghann.android.movieum.ui.search.SearchActivity
import com.tengtonghann.android.movieum.utils.NetworkUtils
import com.tengtonghann.android.movieum.utils.getColorRes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var mBinding: ActivityMainBinding

    companion object {
        const val TAG = "MainActivityLog"
        const val ANIMATION_DURATION = 1000.toLong()
    }

    /**
     * Inject ViewModel [MainViewModel]
     */
    override val mViewModel: MainViewModel by viewModels()

    private var activeFragment: Fragment? = null

    override fun setContentView() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel.onCreated()
    }

    override fun setupView() {
        mBinding.bottomNavigation.run {
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

    override fun setUpObservers() {

        mViewModel.homeNavigationLiveData.observe(this) {
            it.getIfNotHandled()?.run {
                showHome()
            }
        }

        mViewModel.favoriteNavigationLiveData.observe(this) {
            it.getIfNotHandled()?.run { showFavorite() }
        }

        mViewModel.searchLiveData.observe(this) {
            val search = findViewById<View>(R.id.searchView)
            search.visibility = if (it) View.VISIBLE else View.GONE
        }

        handleNetworkChanges()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchView -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Observe network changes: Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(
            this,
            Observer { isConnected ->
                if (!isConnected) {
                    mBinding.textViewNetworkStatus.text =
                        getString(R.string.text_no_connectivity)
                    mBinding.networkStatusLayout.apply {
                        visibility = View.VISIBLE // show
                        setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                    }
                } else {
                    mBinding.textViewNetworkStatus.text =
                        getString(R.string.text_connectivity)
                    mBinding.networkStatusLayout.apply {
                        setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                        // TODO: fixed animation
                        animate()
                            .alpha(1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    visibility = View.GONE
                                }
                            })
                    }
                }
            }
        )
    }

}