package com.github.mariemmezghani.photosearch.history

import android.content.SearchRecentSuggestionsProvider

/**
 * The content provider needed for recent user search history suggestions
 */
class HistoryProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.github.mariemmezghani.photosearch.history.HistoryProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}