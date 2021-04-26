package com.github.mariemmezghani.photosearch.repository

import com.github.mariemmezghani.photosearch.network.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PhotosRepositoryTest{
    val photoResponse1:PhotoResponse= PhotoResponse("51139554989","8ea675253e","65535",66)
    val photoResponse2:PhotoResponse= PhotoResponse("51138104562","90c3f98097","65535",66)
    private val remotePhotos= listOf(photoResponse1,photoResponse2).sortedBy { it.id }
    private lateinit var remotePhotoDataSource:FakeDataSource

    // class under test
    private lateinit var photosRepository: PhotosRepository
    @Before
    fun createRepository(){
        remotePhotoDataSource= FakeDataSource(photos = remotePhotos.toMutableList())
        //Get a reference to the class under test
        photosRepository= PhotosRepository(remotePhotoDataSource)

    }
    @ExperimentalCoroutinesApi
    @Test
    fun getPhotos_LoadAllPhotosFromApi()=runBlockingTest{
        val photos=photosRepository.getPhotos("nature")
        assertThat(photos, IsEqual(remotePhotos))

    }
}