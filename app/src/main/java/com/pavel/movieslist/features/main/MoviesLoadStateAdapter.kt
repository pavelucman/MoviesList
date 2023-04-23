package com.pavel.movieslist.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pavel.movieslist.R
import com.pavel.movieslist.databinding.LoadStateFooterBinding

class MoviesLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        //pass the load state to be in the view holder
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //on retry button retry the failed load state
            binding.bnRetry.setOnClickListener {
                retry()
            }
        }

        /**
         * set the views to be run by loading states
         */
        fun bind(loadState: LoadState) {
            binding.apply {
                pbLoadState.isVisible = loadState is LoadState.Loading
                bnRetry.isVisible = loadState is LoadState.Error
                tvError.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    tvError.text = loadState.error.localizedMessage
                        ?: binding.root.context.getString(R.string.unknown_error_occurred)
                }
            }
        }
    }
}