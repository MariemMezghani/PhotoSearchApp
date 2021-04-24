package com.github.mariemmezghani.photosearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.github.mariemmezghani.photosearch.domain.Photo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mariemmezghani.photosearch.databinding.PhotoItemViewBinding


class PhotoAdapter: ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PhotoAdapter.PhotoViewHolder {
        return PhotoViewHolder(PhotoItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        val photo = getItem(position)
         holder.bind(photo)

    }
    class PhotoViewHolder(private var binding: PhotoItemViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Photo){
            binding.photo=photo
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}