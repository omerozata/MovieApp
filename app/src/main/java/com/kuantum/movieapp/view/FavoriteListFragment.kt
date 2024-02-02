package com.kuantum.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuantum.movieapp.R
import com.kuantum.movieapp.adapter.FavListRecyclerAdapter
import com.kuantum.movieapp.databinding.FragmentFavoriteListBinding
import com.kuantum.movieapp.viewmodel.FavoriteListViewModel
import javax.inject.Inject

class FavoriteListFragment @Inject constructor(
    private val adapter: FavListRecyclerAdapter
) : Fragment(R.layout.fragment_favorite_list) {

    private var fragmentBinding: FragmentFavoriteListBinding? = null
    private lateinit var viewModel: FavoriteListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[FavoriteListViewModel::class.java]

        val binding = FragmentFavoriteListBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        adapter.onItemClick = {
            findNavController().navigate(
                FavoriteListFragmentDirections.actionFavoriteListFragmentToFavMovieDetailsFragment(it))
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun subscribeToObservers() {
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.favList = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}