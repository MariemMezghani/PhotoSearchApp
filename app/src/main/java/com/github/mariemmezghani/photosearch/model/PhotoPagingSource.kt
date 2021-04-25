package com.github.mariemmezghani.photosearch.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.mariemmezghani.photosearch.network.ApiService
import com.github.mariemmezghani.photosearch.network.asDomainModel
import retrofit2.HttpException
import java.io.IOException

private const val Photos_STARTING_PAGE_INDEX = 1

class PhotoPagingSource(private val service: ApiService, private val query: String) :
    PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: Photos_STARTING_PAGE_INDEX
        return try {
            val response =
                service.getSearchedPhotos(query, position, params.loadSize)
                    .asDomainModel()
            LoadResult.Page(
                data = response,
                prevKey = if (position == Photos_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}