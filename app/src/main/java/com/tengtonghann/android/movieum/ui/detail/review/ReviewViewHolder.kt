package com.tengtonghann.android.movieum.ui.detail.review

import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.tengtonghann.android.movieum.databinding.ItemReviewBinding
import com.tengtonghann.android.movieum.model.Review
import java.util.*

class ReviewViewHolder(private val binding: ItemReviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(review: Review) {
        binding.frame.clipToOutline = false
        val authorName = review.author
        binding.textAuthor.text = authorName
        binding.textContent.text = review.content
        val generatorColor = ColorGenerator.MATERIAL.randomColor
        val drawable = TextDrawable.builder().buildRound(
            authorName?.substring(0, 1)
                ?.toUpperCase(Locale.ROOT), generatorColor
        )
        binding.imageAuthor.setImageDrawable(drawable)
    }
}