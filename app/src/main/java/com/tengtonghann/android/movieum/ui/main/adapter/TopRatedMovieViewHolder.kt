package com.tengtonghann.android.movieum.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ItemMovieBinding
import com.tengtonghann.android.movieum.model.Movie
import com.tengtonghann.android.movieum.model.TopRatedMovie

class TopRatedMovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(topRatedMovie: TopRatedMovie) {
        binding.movieTitle.text = topRatedMovie.title

        Glide.with(itemView)
            .load(IMAGE_BASE_URL + IMAGE_SIZE_W780 + topRatedMovie.posterPath)
            .placeholder(R.drawable.ic_photo)
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .into(binding.movieImageView)
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W780 = "w780"
    }
}