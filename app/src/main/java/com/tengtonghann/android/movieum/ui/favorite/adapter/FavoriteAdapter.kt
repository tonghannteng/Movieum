package com.tengtonghann.android.movieum.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tengtonghann.android.movieum.databinding.ItemMovieBinding
import com.tengtonghann.android.movieum.model.FavoriteMovie
import com.tengtonghann.android.movieum.ui.favorite.viewHolder.FavoriteViewHolder

/**
 * @author Tonghann Teng
 */
class FavoriteAdapter(private val onItemClicked: (FavoriteMovie) -> Unit) :
    ListAdapter<FavoriteMovie, FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteMovie>() {
            override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FavoriteMovie,
                newItem: FavoriteMovie
            ): Boolean = oldItem == newItem

        }
    }
}