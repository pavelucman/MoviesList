package com.pavel.movieslist.features.detail

import androidx.lifecycle.ViewModel
import com.pavel.movieslist.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DefaultRepository) : ViewModel() {

    fun getTopMovies(movieId: Int) = repository.getMovieDetails(movieId)
}