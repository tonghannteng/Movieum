package com.tengtonghann.android.movieum.ui.detail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tengtonghann.android.movieum.databinding.ItemReviewBinding
import com.tengtonghann.android.movieum.model.Review

class ReviewAdapter : ListAdapter<Review, ReviewViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Review,
                newItem: Review
            ): Boolean =
                oldItem == newItem

        }
    }
}