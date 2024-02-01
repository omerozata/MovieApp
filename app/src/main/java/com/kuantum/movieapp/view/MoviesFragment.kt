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
import com.kuantum.movieapp.util.Constants.DEFAULT_SEARCH
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
    private var page: Int = 1
    private var search: String = DEFAULT_SEARCH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MoviesViewModel::class.java]

        val binding = FragmentMoviesBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        adapter.onItemClick = {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToFragmentMovieDetails(it)
            )
        }

        binding.btnLeft.setOnClickListener {
            if (page > 1) {
                page--
                viewModel.getMovies(search, page)
                viewModel.setPage(page)
            }
        }

        binding.btnRight.setOnClickListener {
            page++
            viewModel.getMovies(search, page)
            viewModel.setPage(page)
        }

        var job: Job? = null
        binding.editSearch.addTextChangedListener {
            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)

                it?.let {
                    if (it.toString() == search)
                        return@launch
                    if (it.toString().isEmpty())
                        return@launch
                    search = it.toString()
                    page = 1
                    viewModel.setPage(page)
                    viewModel.getMovies(search, page)
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
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

        viewModel.page.observe(viewLifecycleOwner, Observer {
            page = it
            fragmentBinding!!.txtPageNumber.text = page.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }

}