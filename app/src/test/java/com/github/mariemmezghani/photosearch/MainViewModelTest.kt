package com.github.mariemmezghani.photosearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.github.mariemmezghani.photosearch.model.Photo
import com.github.mariemmezghani.photosearch.network.ApiService
import org.junit.Assert.*
import org.junit.Rule
import com.github.mariemmezghani.photosearch.repository.PhotosRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class MainViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel
    @Mock
    lateinit var repository:PhotosRepository
    @Mock
    val api:ApiService= ApiService.create()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.repository= PhotosRepository(this.api)
        this.mainViewModel = MainViewModel(this.repository)
    }

    @Test
    fun getPhotosTest_NotNull(){
        //Invoke
        mainViewModel.getPhotosList("nature")
        // verify
        assertNotNull(this.mainViewModel.photos)
    }
    @Test
    fun getPhotosTest(){
        // Mock API response
        Mockito.`when`(repository.getPhotos(ArgumentMatchers.anyString())).thenAnswer {
            return@thenAnswer (Maybe.just(ArgumentMatchers.anyList<Photo>()))
        }
        // Attacch fake observer
        val observer = mock(Observer::class.java) as Observer<LiveData<PagingData<Photo>>>
        this.mainViewModel.photos.observeForever(observer)

        //Invoke
        mainViewModel.getPhotosList(ArgumentMatchers.anyString())
        // verify
        assertEquals(LiveData<PagingData<Photo>>.Status.SUCCESS, this.mainViewModel.photos?.value.status)
    }

}