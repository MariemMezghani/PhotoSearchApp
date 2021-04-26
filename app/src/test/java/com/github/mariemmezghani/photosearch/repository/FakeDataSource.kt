package com.github.mariemmezghani.photosearch.repository

import com.github.mariemmezghani.photosearch.network.ApiService
import com.github.mariemmezghani.photosearch.network.PhotoResponse

class FakeDataSource(var photos:MutableList<PhotoResponse> = mutableListOf()):ApiService {
    override suspend fun getSearchedPhotos(
        searchTerm: String,
        page: Int,
        itemsPerPage: Int
    ): List<PhotoResponse> {

        return ArrayList(photos)
    }
}