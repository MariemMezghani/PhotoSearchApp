package com.github.mariemmezghani.photosearch.network

import com.github.mariemmezghani.photosearch.model.Photo

data class PhotoResponse(val id: String, val secret: String, val server: String, val farm: Int)
fun List<PhotoResponse>.asDomainModel(): List<Photo>{
    return map {
        Photo(
            it.id,
            "https://farm${it.farm}.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg"

        )
    }
}