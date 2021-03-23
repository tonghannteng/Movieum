package com.tengtonghann.android.movieum.ui.detail.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tengtonghann.android.movieum.databinding.ItemTrailerBinding
import com.tengtonghann.android.movieum.model.Trailer

class TrailerAdapter(
    private val onTrailerClick: (trailerKey: String) -> Unit
) : ListAdapter<Trailer, TrailerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TrailerViewHolder(
            ItemTrailerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(getItem(position), onTrailerClick)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Trailer>() {
            override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Trailer,
                newItem: Trailer
            ): Boolean = oldItem == newItem

        }
    }
}