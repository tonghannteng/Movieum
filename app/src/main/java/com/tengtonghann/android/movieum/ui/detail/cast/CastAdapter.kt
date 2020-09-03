package com.tengtonghann.android.movieum.ui.detail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tengtonghann.android.movieum.databinding.ItemCastBinding
import com.tengtonghann.android.movieum.model.Cast

class CastAdapter : ListAdapter<Cast, CastViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CastViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Cast,
                newItem: Cast
            ): Boolean = oldItem == newItem
        }
    }
}