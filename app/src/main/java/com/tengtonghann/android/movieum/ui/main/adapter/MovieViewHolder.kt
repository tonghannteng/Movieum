package com.tengtonghann.android.movieum.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ItemMovieBinding
import com.tengtonghann.android.movieum.model.Movie

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.movieImageView.load(IMAGE_BASE_URL + IMAGE_SIZE_W185 + movie.posterPath) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W185 = "w185"
    }
}