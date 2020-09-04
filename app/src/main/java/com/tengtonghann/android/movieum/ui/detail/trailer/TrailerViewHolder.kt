package com.tengtonghann.android.movieum.ui.detail.trailer

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ItemTrailerBinding
import com.tengtonghann.android.movieum.model.Trailer

class TrailerViewHolder(private val binding: ItemTrailerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(trailer: Trailer, onTrailerClick: (trailerKey: String) -> Unit) {
        binding.trailerName.text = trailer.title
        val thumbnail = "${YOUTUBE_BASE_URL}${trailer.key}${IMAGE_FORMAT}"
        Glide.with(itemView)
            .load(thumbnail)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_broken_image)
            .into(binding.imageTrailer)

        binding.cardTrailer.setOnClickListener {
            trailer.key?.let { key ->
                onTrailerClick(key)
            }
        }
    }

    companion object {
        const val YOUTUBE_BASE_URL = "https://img.youtube.com/vi/"
        const val IMAGE_FORMAT = "/hqdefault.jpg"
    }
}