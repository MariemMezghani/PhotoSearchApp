package com.github.mariemmezghani.photosearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.github.mariemmezghani.photosearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        binding.searchText.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // make sure the recycler view scrolls to position 0
                    binding.photosRecyclerview.scrollToPosition(0)
                    viewModel.getPhotosList(query)
                    binding.searchText.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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