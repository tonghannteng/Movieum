package com.tengtonghann.android.movieum.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tengtonghann.android.movieum.databinding.ItemMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.TopRatedMovie

/**
 * Adapter class for [RecyclerView] which bind [Movie]
 */
class TopRatedMovieAdapter : ListAdapter<TopRatedMovie, TopRatedMovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TopRatedMovieViewHolder(
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopRatedMovie>() {
            override fun areItemsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie): Boolean =
                oldItem == newItem

        }
    }
}