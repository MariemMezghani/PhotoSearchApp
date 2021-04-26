package com.github.mariemmezghani.photosearch

import androidx.lifecycle.*
import com.github.mariemmezghani.photosearch.repository.PhotosRepository
import androidx.paging.cachedIn
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.switchMap
import androidx.lifecycle.ViewModel


class MainViewModel(private val repository: PhotosRepository) : ViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val photos = currentQuery.switchMap { queryString ->
        repository.getPhotos(queryString).cachedIn(viewModelScope)
    }

    init {
        getPhotosList(currentQuery.value ?: "")
    }


    fun getPhotosList(query: String) {
        currentQuery.value = query

    }

    companion object {
        private const val DEFAULT_QUERY = "nature"
    }

}