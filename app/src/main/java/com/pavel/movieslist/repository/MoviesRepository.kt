package com.pavel.movieslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pavel.movieslist.BuildConfig
import com.pavel.movieslist.api.MoviesApi
import com.pavel.movieslist.api.models.MovieDetails
import com.pavel.movieslist.api.models.TopRatedMovies
import com.pavel.movieslist.features.main.MoviesPagingSource
import com.pavel.movieslist.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
) : DefaultRepository {

    override fun getTopMovies(
    ): Flow<PagingData<TopRatedMovies.Result>> {
        return Pager(PagingConfig(pageSize = 20)) {
            MoviesPagingSource(moviesApi)
        }.flow
    }

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Resource.loading(null))

            try {
                val response =
                    moviesApi.getMovieDetail(movieId = movieId, BuildConfig.MOVIES_ACCESS_KEY)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    emit(Resource.error(response.errorBody().toString(), null))
                }
            } catch (e: Exception) {
                emit(Resource.error(e.message ?: "An error occurred", null))
            }

        }.flowOn(Dispatchers.IO)

    }
}