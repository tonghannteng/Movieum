package com.tengtonghann.android.movieum.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Abstract Activity which binds [ViewModel]  [ViewBinding]
 */

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected abstract val mViewModel: VM

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getViewBinding()
        setContentView()
        setupView()
        setUpObservers()
    }

    /**
     * Returns [VB] which is assigned to [mViewBinding]
     */
    protected abstract fun getViewBinding(): VB
    protected abstract fun setContentView()
    protected abstract fun setupView()
    protected abstract fun setUpObservers()
}