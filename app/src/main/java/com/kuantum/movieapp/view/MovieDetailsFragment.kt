package com.kuantum.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.kuantum.movieapp.R
import com.kuantum.movieapp.adapter.MoviesRecyclerAdapter
import com.kuantum.movieapp.databinding.FragmentMovieDetailsBinding
import com.kuantum.movieapp.util.Resource
import com.kuantum.movieapp.viewmodel.MovieDetailsViewModel
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(
    private val glide : RequestManager
) : Fragment(R.layout.fragment_movie_details) {

    private var fragmentBinding: FragmentMovieDetailsBinding? = null
    private lateinit var viewModel: MovieDetailsViewModel
    private var id: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MovieDetailsViewModel::class.java]

        val binding = FragmentMovieDetailsBinding.bind(view)
        fragmentBinding = binding

        arguments?.let {
            id = MovieDetailsFragmentArgs.fromBundle(it).id
            viewModel.getMovieDetails(id = id)
            println(id)
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Loading -> {
                    fragmentBinding!!.txtError.visibility = View.GONE
                    fragmentBinding!!.progressBar.visibility = View.VISIBLE
                    fragmentBinding!!.linearlayout.visibility = View.GONE
                }

                is Resource.Error -> {
                    fragmentBinding!!.txtError.visibility = View.VISIBLE
                    fragmentBinding!!.progressBar.visibility = View.GONE
                    fragmentBinding!!.linearlayout.visibility = View.GONE
                }

                is Resource.Success -> {
                    fragmentBinding!!.txtError.visibility = View.GONE
                    fragmentBinding!!.progressBar.visibility = View.GONE
                    fragmentBinding!!.linearlayout.visibility = View.VISIBLE

                    it.data?.let { movieDetails ->
                        fragmentBinding!!.txtTitle.text = movieDetails.title
                        fragmentBinding!!.txtYear.text = movieDetails.year
                        fragmentBinding!!.txtActors.text = movieDetails.actors
                        glide.load(movieDetails.poster).into(fragmentBinding!!.imageviewPoster)
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