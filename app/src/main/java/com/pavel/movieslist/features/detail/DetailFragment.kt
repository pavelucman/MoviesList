package com.pavel.movieslist.features.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.pavel.movieslist.R
import com.pavel.movieslist.api.MoviesApi
import com.pavel.movieslist.databinding.DetailFragmentBinding
import com.pavel.movieslist.util.Status
import com.pavel.movieslist.util.loadImageNoCorners
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    //initiate the view model
    private val viewModel: DetailViewModel by viewModels()

    //take the safe args from the navigation
    private val args: DetailFragmentArgs by navArgs()

    //get the DetailFragmentBinding
    private lateinit var currentBinding: DetailFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = DetailFragmentBinding.bind(view)

        val movieId = args.movieId
        lifecycleScope.launchWhenCreated {
            viewModel.getTopMovies(movieId).collectLatest { movieDetail ->

                //when the status is in loading show the spinner
                currentBinding.pbDetailMovie.isVisible = movieDetail.status == Status.LOADING

                when (movieDetail.status) {
                    Status.SUCCESS -> {
                        //if the http call is 200 fill the layout
                        currentBinding.apply {
                            ivDetail.loadImageNoCorners("${MoviesApi.IMAGE_SOURCE}${movieDetail.data?.backdrop_path}")
                            tvTitle.text = movieDetail.data?.title
                            tvDescription.text = movieDetail.data?.overview
                            val originalLanguage =
                                "Original language: ${movieDetail.data?.original_language}"
                            tvLanguage.text = originalLanguage
                        }
                    }

                    Status.ERROR -> {
                        currentBinding.tvErrorDetail.text = movieDetail.message
                    }
                }
            }
        }
    }
}