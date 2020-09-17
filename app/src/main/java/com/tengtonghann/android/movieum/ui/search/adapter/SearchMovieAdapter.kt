package com.tengtonghann.android.movieum.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tengtonghann.android.movieum.databinding.ItemSearchMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.ui.search.viewHolder.SearchMovieHolder

class SearchMovieAdapter(
    private val onItemClicked: (Movie, ImageView) -> Unit
) : ListAdapter<Movie, SearchMovieHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchMovieHolder(
            ItemSearchMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchMovieHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean =
                oldItem == newItem

        }
    }
}