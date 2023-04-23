package com.pavel.movieslist.features.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavel.movieslist.R
import com.pavel.movieslist.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    //instantiate the view model with hilt injection
    private val viewModel: MainViewModel by viewModels()

    //get the movies paging adapter
    private lateinit var moviesPagingAdapter: MoviesListPagingAdapter

    //get the MainFragmentBinding
    private lateinit var currentBinding: MainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = MainFragmentBinding.bind(view)

        //on item click from the view holder pass the pojo object
        moviesPagingAdapter = MoviesListPagingAdapter(
            onItemClick = { movie ->
                //navigate to the other fragment and pass the id of the movie
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id)
                view.findNavController().navigate(action)
            }
        )

        currentBinding.apply {
            rvMovies.apply {
                layoutManager = LinearLayoutManager(activity)
                // set the footer to the MoviesPagingAdapter
                adapter = moviesPagingAdapter
                    .withLoadStateFooter(
                        MoviesLoadStateAdapter(moviesPagingAdapter::retry)
                    )
                setHasFixedSize(true)
            }

            //get top movies on Lifecycle.State.CREATED of fragment
            lifecycleScope.launchWhenCreated {
                viewModel.getTopMovies().collect {
                    moviesPagingAdapter.submitData(it)
                }
            }

            lifecycleScope.launchWhenStarted {
                moviesPagingAdapter.loadStateFlow
                    .collect { loadStates ->
                        //with load states control what is showed on the screen
                        pbMain.isVisible = loadStates.refresh is LoadState.Loading
                        bnRetryMain.isVisible = loadStates.refresh is LoadState.Error
                        tvErrorMain.isVisible = loadStates.refresh is LoadState.Error
                    }
            }
            bnRetryMain.setOnClickListener {
                moviesPagingAdapter.retry()
            }
        }
    }
}