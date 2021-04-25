package com.github.mariemmezghani.photosearch

import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photosearch.network.ApiService
import com.github.mariemmezghani.photosearch.repository.PhotosRepository

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [PhotosRepository] based on the [ApiService]
     */
    private fun providePhotosRepository(): PhotosRepository {
        return PhotosRepository(ApiService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [MainViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory(providePhotosRepository())
    }
}