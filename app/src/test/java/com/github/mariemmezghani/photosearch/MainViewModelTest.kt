package com.github.mariemmezghani.photosearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.mariemmezghani.photosearch.network.ApiService
import org.junit.Assert.*
import org.junit.Rule
import com.github.mariemmezghani.photosearch.repository.PhotosRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mainViewModel: MainViewModel
    lateinit var repository:PhotosRepository
    val api:ApiService= ApiService.create()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.repository= PhotosRepository(this.api)
        this.mainViewModel = MainViewModel(this.repository)
    }

    @Test
    fun getPhotosTest(){
        //Invoke
        mainViewModel.getPhotosList("nature")
        // verify
        assertNotNull(this.mainViewModel.photos.value)
    }

}