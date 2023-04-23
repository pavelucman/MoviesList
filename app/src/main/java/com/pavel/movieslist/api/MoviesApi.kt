package com.pavel.movieslist.api

import com.pavel.movieslist.api.models.MovieDetails
import com.pavel.movieslist.api.models.TopRatedMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
        const val IMAGE_SOURCE = "https://image.tmdb.org/t/p/original"
    }

    @GET("/3/movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TopRatedMovies

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieDetails>
}