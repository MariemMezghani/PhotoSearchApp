package com.github.mariemmezghani.photosearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photosearch.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    val adapter = PhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMainBinding.inflate(inflater)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MainViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.photosRecyclerview.adapter = adapter

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

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


        return binding.root
    }

}

