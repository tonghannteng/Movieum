package com.tengtonghann.android.movieum.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ItemSearchMovieBinding
import com.tengtonghann.android.movieum.model.Movie

class SearchMovieHolder(
    private val binding: ItemSearchMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(searchMovie: Movie) {
        binding.movieTitle.text = searchMovie.title
        binding.movieOverview.text = searchMovie.overview
        Glide.with(itemView)
            .load(IMAGE_BASE_URL + IMAGE_SIZE_W780 + searchMovie.posterPath)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_broken_image)
            .into(binding.movieImageView)
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W780 = "w780"
    }
}