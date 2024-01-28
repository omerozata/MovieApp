package com.kuantum.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuantum.movieapp.R
import com.kuantum.movieapp.adapter.MoviesRecyclerAdapter
import com.kuantum.movieapp.databinding.FragmentMoviesBinding
import com.kuantum.movieapp.util.Resource
import com.kuantum.movieapp.viewmodel.MoviesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class MoviesFragment @Inject constructor(
    private val adapter: MoviesRecyclerAdapter
) : Fragment(R.layout.fragment_movies) {

    private var fragmentBinding: FragmentMoviesBinding? = null
    private lateinit var viewModel: MoviesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MoviesViewModel::class.java]

        val binding = FragmentMoviesBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        adapter.onItemClick = {
            findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToFragmentMovieDetails(it))
        }

        var job : Job? = null
        binding.editSearch.addTextChangedListener {
            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)

                it?.let {
                    viewModel.getMovies(it.toString())
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.movies.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is Resource.Loading -> {
                    fragmentBinding!!.progressBar.visibility = View.VISIBLE
                    fragmentBinding!!.txtError.visibility = View.GONE
                    adapter.movieList = arrayListOf()
                }

                is Resource.Error -> {
                    fragmentBinding!!.progressBar.visibility = View.GONE
                    fragmentBinding!!.txtError.visibility = View.VISIBLE
                    adapter.movieList = arrayListOf()
                }

                is Resource.Success -> {
                    fragmentBinding!!.progressBar.visibility = View.GONE
                    fragmentBinding!!.txtError.visibility = View.GONE
                    it.data?.let { movieList ->
                        adapter.movieList = movieList
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }

}