package com.github.mariemmezghani.photosearch

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mariemmezghani.photosearch.domain.Photo
import com.squareup.picasso.Picasso

@BindingAdapter("photo")
fun bindPhoto(imageView: ImageView, url: String?) {
    url?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Picasso.get()
            .load(imgUri)
            //.placeholder(R.drawable.placeholder_picture_of_day)
            .into(imageView)
    }
}
@BindingAdapter("listView")
fun bindRecyclerView(recyclerView: RecyclerView, data:List<Photo>?){
    val adapter=recyclerView.adapter as PhotoAdapter
    adapter.submitList(data)

}
