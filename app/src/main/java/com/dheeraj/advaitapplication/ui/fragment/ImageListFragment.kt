package com.dheeraj.advaitapplication.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.dheeraj.advaitapplication.adapter.UnsplashPhotoAdapter
import com.dheeraj.advaitapplication.data.UnsplashPhoto
import com.dheeraj.advaitapplication.databinding.FragmentImageListBinding
import com.dheeraj.advaitapplication.viewmodel.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.simpleunsplashapp.ui.gallery.UnsplashPhotoLoadStateAdapter

@AndroidEntryPoint
class ImageListFragment : Fragment(), UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<UnsplashViewModel>()

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentImageListBinding.bind(requireView())

        val adapter = UnsplashPhotoAdapter(this)

        binding?.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener { adapter.retry() }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding?.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        //TODO
    }

}