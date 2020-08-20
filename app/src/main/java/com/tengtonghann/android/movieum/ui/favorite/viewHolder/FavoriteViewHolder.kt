package com.tengtonghann.android.movieum.ui.favorite.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tengtonghann.android.movieum.R
import com.tengtonghann.android.movieum.databinding.ItemFavoriteMovieBinding
import com.tengtonghann.android.movieum.databinding.ItemMovieBinding
import com.tengtonghann.android.movieum.model.FavoriteMovie

class FavoriteViewHolder(private val binding: ItemFavoriteMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(favoriteMovie: FavoriteMovie, onItemClicked: (FavoriteMovie) -> Unit) {
        binding.movieTitle.text = favoriteMovie.title
        binding.movieOverview.text = favoriteMovie.overview
        binding.addToFavorite.setImageResource(R.drawable.ic_heart_selected)
        binding.addToFavorite.setOnClickListener {
            onItemClicked(favoriteMovie)
        }

        Glide.with(itemView)
            .load(IMAGE_BASE_URL + IMAGE_SIZE_W780 + favoriteMovie.posterPath)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_broken_image)
            .into(binding.movieImageView)

    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE_W780 = "w780"
    }
}