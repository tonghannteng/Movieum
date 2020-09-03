package com.tengtonghann.android.movieum.ui.detail.cast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ItemCastBinding
import com.tengtonghann.android.movieum.model.Cast

class CastViewHolder(private val binding: ItemCastBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cast: Cast) {
        binding.textCastName.text = cast.actorName
        Glide.with(itemView)
            .load(IMAGE_BASE_URL + IMAGE_SIZE_W185 + cast.profileImagePath)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_broken_image)
            .into(binding.imageCastProfile)
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W185 = "w185"
    }
}