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


@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var repository: PhotosRepository

    @Mock
    lateinit var api: ApiService

    @Mock
    lateinit var query: String


    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)
        this.api = ApiService.create()
        this.repository = PhotosRepository(this.api)
        this.mainViewModel = MainViewModel(this.repository)
        this.query = "tree"
    }

    @Test
    fun getPhotosTest_NotNull() {
        //Invoke
        mainViewModel.getPhotosList("nature")
        // verify
        assertNotNull(this.mainViewModel.photos)
    }

}