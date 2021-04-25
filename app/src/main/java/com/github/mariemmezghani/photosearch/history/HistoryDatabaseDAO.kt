package com.github.mariemmezghani.photosearch.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDatabaseDAO {

    @Query("SELECT * from history_table ")
    fun getHistory():LiveData<List<SearchedItem>>
}