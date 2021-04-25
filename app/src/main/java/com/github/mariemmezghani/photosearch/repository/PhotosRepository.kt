package com.github.mariemmezghani.photosearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.github.mariemmezghani.photosearch.model.PhotoPagingSource
import com.github.mariemmezghani.photosearch.network.ApiService

class PhotosRepository(private val service: ApiService) {
    fun getPhotos(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotoPagingSource(service, query) }
    ).liveData
}
