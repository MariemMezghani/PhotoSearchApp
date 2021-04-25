package com.github.mariemmezghani.photosearch

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.mariemmezghani.photosearch.history.HistoryDatabase
import com.github.mariemmezghani.photosearch.history.HistoryDatabaseDAO
import com.github.mariemmezghani.photosearch.history.SearchedItem
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HistoryDatabaseTest {

    private lateinit var historyDao: HistoryDatabaseDAO
    private lateinit var db: HistoryDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, HistoryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        historyDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insert() {
        val searchedItem = SearchedItem()
       // historyDao.insert(searchedItem)
        assertEquals(searchedItem.query, "")
    }
}