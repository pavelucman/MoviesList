package com.pavel.movieslist.features.main

import androidx.recyclerview.widget.RecyclerView
import com.pavel.movieslist.api.MoviesApi
import com.pavel.movieslist.api.models.TopRatedMovies
import com.pavel.movieslist.databinding.ItemMoviesBinding
import com.pavel.movieslist.util.loadImage

class MoviesViewHolder(
    private val binding: ItemMoviesBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: TopRatedMovies.Result) {

        binding.apply {
            tvTitle.text = movie.title
            tvDescription.text = movie.overview

            ivMovieImage.loadImage("${MoviesApi.IMAGE_SOURCE}${movie.backdrop_path}")

            root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
        }
    }
}