package com.github.mariemmezghani.photosearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mariemmezghani.photosearch.domain.Photo
import com.github.mariemmezghani.photosearch.network.PhotoApi
import com.github.mariemmezghani.photosearch.network.asDomainModel
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val status: LiveData<String>
        get() = _status

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    init {
        getPhotosList()
    }


    private fun getPhotosList() {
        viewModelScope.launch {
            try {
                val listResult = PhotoApi.retrofitService.getSearchedPhotos("dog").asDomainModel()
                if (listResult.size >0) {
                    _photos.value = listResult
                }

            } catch (e: Exception) {
                _status.value = "Failure"
            }

        }

    }

}