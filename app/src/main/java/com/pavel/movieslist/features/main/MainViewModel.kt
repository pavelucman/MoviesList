package com.pavel.movieslist.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pavel.movieslist.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    /**
     * fetch all the top movies and pass it to flow stream, the flow is kept active as long as the given scope is active
     */
    fun getTopMovies() = repository.getTopMovies().cachedIn(viewModelScope)
}