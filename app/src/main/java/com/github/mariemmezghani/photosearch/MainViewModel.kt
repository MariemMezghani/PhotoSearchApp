package com.github.mariemmezghani.photosearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mariemmezghani.photosearch.network.Photo
import com.github.mariemmezghani.photosearch.network.PhotoApi
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val status: LiveData<String>
        get() = _status

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo>
        get() = _photo
    val _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String>
        get() = _photoUrl

    init {
        getPhotosList()
    }


    private fun getPhotosList() {
        viewModelScope.launch {
            try {
                val listResult = PhotoApi.retrofitService.getSearchedPhotos("dog")
                if (listResult.size > 0) {
                    _photo.value = listResult.get(0)
                    _photoUrl.value =
                        "https://farm${_photo.value?.farm}.staticflickr.com/${_photo.value?.server}/${_photo.value?.id}_${_photo.value?.secret}.jpg"
                }
            } catch (e: Exception) {
                _status.value = "Failure"
            }

        }

    }

}