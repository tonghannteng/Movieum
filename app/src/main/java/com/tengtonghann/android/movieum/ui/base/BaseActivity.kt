package com.tengtonghann.android.movieum.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Abstract Activity which binds [ViewModel]  [ViewBinding]
 */

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected abstract val mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        setupView()
        setUpObservers()
    }

    protected abstract fun setContentView()
    protected abstract fun setupView()
    protected abstract fun setUpObservers()
}