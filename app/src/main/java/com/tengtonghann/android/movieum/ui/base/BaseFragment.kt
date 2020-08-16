package com.tengtonghann.android.movieum.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Abstract Fragment which binds [ViewModel] [ViewBinding]
 */
abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {

    protected abstract val mViewModel: VM
    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(provideLayoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding = getViewBinding(view)
        setupView(view)
    }

    /**
     * Returns [VB] which is assigned to [mViewBinding]
     */
    protected abstract fun getViewBinding(view: View): VB

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView(view: View)

    protected abstract fun initCreate()
}