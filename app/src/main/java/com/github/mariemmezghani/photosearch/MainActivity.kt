package com.github.mariemmezghani.photosearch

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.widget.CursorAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.github.mariemmezghani.photosearch.databinding.ActivityMainBinding
import com.github.mariemmezghani.photosearch.history.HistoryProvider


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // make the Activity searchable
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchText.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }
        binding.searchText.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // make sure the recycler view scrolls to position 0
                    binding.photosRecyclerview.scrollToPosition(0)
                    viewModel.getPhotosList(query)
                    binding.searchText.clearFocus()
                    query.also { query ->
                        SearchRecentSuggestions(
                            this@MainActivity,
                            HistoryProvider.AUTHORITY,
                            HistoryProvider.MODE
                        )
                            .saveRecentQuery(query, null)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.searchText
            .setOnSuggestionListener(object : androidx.appcompat.widget.SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val selectedView: androidx.cursoradapter.widget.CursorAdapter? = binding.searchText.getSuggestionsAdapter()
                val cursor: Cursor = selectedView?.getItem(position) as Cursor
                val index: Int = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1)
                binding.searchText.setQuery(cursor.getString(index), true)
                return true
            }
        })
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MainViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.photosRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PhotoLoadStateAdapter { adapter.retry() },
            footer = PhotoLoadStateAdapter { adapter.retry() },
        )
        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }

        viewModel.photos.observe(this) {
            adapter.submitData(this.lifecycle, it)

        }


        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                photosRecyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    photosRecyclerview.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

    }

}