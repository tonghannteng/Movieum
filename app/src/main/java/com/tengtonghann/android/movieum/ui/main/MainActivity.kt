package com.tengtonghann.android.movieum.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ActivityMainBinding
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.ui.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        const val TAG = "MainActivity"
    }

    override val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        setupView()
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private fun setupView() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(MovieFragment.TAG) as MovieFragment?

        if (fragment == null) {
            fragment = MovieFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, MovieFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commit()
    }

}