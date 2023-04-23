package com.pavel.movieslist.features.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pavel.movieslist.BuildConfig
import com.pavel.movieslist.api.MoviesApi
import com.pavel.movieslist.api.models.TopRatedMovies
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(private val moviesApi: MoviesApi) :
    PagingSource<Int, TopRatedMovies.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopRatedMovies.Result> {
        return try {
            // get the current key
            val currentLoadingPageKey = params.key ?: 1
            //start the http call with page 1
            val response = moviesApi.getTopMovies(
                apiKey = BuildConfig.MOVIES_ACCESS_KEY,
                language = "en",
                page = currentLoadingPageKey
            )
            //get the previous key and set it -1 from the current
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            //every end of page add plus one to the current loading page key
            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopRatedMovies.Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


}