package com.tengtonghann.android.movieum.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.databinding.ItemMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.ui.movie.viewHolder.MovieViewHolder

/**
 * Adapter class for [RecyclerView] which bind [Movie]
 */
class PopularAdapter(
    private val onFavoriteClicked: (Movie) -> Unit,
    private val onItemClicked: (Movie) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position), onFavoriteClicked, onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }
}