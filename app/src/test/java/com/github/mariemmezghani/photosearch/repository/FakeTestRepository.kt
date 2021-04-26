package com.github.mariemmezghani.photosearch.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.github.mariemmezghani.photosearch.model.Photo

class FakeTestRepository:PhotosRepositoryInterface {

    override fun getPhotos(query: String): LiveData<PagingData<Photo>> {
        TODO("Not yet implemented")


    }
}