package com.tengtonghann.android.movieum.ui.search

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.data.state.State
import com.tengtonghann.android.movieum.databinding.ActivitySearchBinding
import com.tengtonghann.android.movieum.ui.base.BaseActivity
import com.tengtonghann.android.movieum.utils.Logger
import com.tengtonghann.android.movieum.utils.getQueryTextChangeStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>(), CoroutineScope {

    private lateinit var searchView: SearchView
    private lateinit var job: Job

    companion object {
        const val TAG = "SearchActivityLog"
    }

    override val mViewModel: SearchViewModel by viewModels()
    private val mSearchMovieAdapter = SearchMovieAdapter()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_screen_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.searchScreenView)
        searchView = searchItem?.actionView as SearchView
        searchView.onActionViewExpanded()

        launch {
            searchView.getQueryTextChangeStateFlow()
                .debounce(800)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    getSearchMovies(result)
                    Logger.d(TAG, result)
                }
        }
        return true

    }

    private fun getSearchMovies(result: String) {
        mViewModel.getSearchMovies(result, 1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (!searchView.isIconified) {
                    searchView.onActionViewCollapsed()
                } else {
                    super.onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun setUpObservers() {
        mViewModel.searchMoviesLiveData.observe(
            this,
            { state ->
                when (state) {
                    is State.Success -> {
                        mSearchMovieAdapter.submitList(state.data.movies)
                    }
                }
            }
        )
    }

    override fun setupView() {
        // actionbar
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowHomeEnabled(true)
        job = Job()
        mViewBinding.searchMovieRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mSearchMovieAdapter
        }

    }

    override fun setContentView() {
        setContentView(mViewBinding.root)
    }

    override fun getViewBinding(): ActivitySearchBinding =
        ActivitySearchBinding.inflate(layoutInflater)
}