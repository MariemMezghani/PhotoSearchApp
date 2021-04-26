package com.github.mariemmezghani.photosearch.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.github.mariemmezghani.photosearch.model.Photo

interface PhotosRepositoryInterface {
    fun getPhotos(query: String): LiveData<PagingData<Photo>>
}