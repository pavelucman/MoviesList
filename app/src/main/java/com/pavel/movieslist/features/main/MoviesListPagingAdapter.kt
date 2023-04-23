package com.pavel.movieslist.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.pavel.movieslist.api.models.TopRatedMovies
import com.pavel.movieslist.databinding.ItemMoviesBinding

class MoviesListPagingAdapter(private val onItemClick: (TopRatedMovies.Result) -> Unit

) : PagingDataAdapter<TopRatedMovies.Result, MoviesViewHolder>(MoviesComparator()) {

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoviesViewHolder(binding, onItemClick = { position ->
            val movie = getItem(position)
            if (movie != null) {
                onItemClick(movie)
            }
        })
    }
}