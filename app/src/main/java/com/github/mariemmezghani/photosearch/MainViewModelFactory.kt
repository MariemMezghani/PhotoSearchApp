package com.github.mariemmezghani.photosearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photosearch.repository.PhotosRepository

class MainViewModelFactory(private val repository: PhotosRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}