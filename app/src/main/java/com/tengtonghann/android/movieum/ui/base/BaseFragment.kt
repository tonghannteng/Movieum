package com.tengtonghann.android.movieum.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Abstract Fragment which binds [ViewModel] [ViewBinding]
 */
abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected abstract val mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }
    protected abstract fun initData()
}