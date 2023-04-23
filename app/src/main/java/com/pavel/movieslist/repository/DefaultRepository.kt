package com.pavel.movieslist.repository

import androidx.paging.PagingData
import com.pavel.movieslist.api.models.MovieDetails
import com.pavel.movieslist.api.models.TopRatedMovies
import com.pavel.movieslist.util.Resource
import kotlinx.coroutines.flow.Flow

interface DefaultRepository {

    fun getTopMovies(): Flow<PagingData<TopRatedMovies.Result>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
}