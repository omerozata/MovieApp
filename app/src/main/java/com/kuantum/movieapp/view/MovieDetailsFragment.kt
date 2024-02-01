package com.kuantum.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.kuantum.movieapp.R
import com.kuantum.movieapp.databinding.FragmentMovieDetailsBinding
import com.kuantum.movieapp.model.SavedMovie
import com.kuantum.movieapp.model.toSavedMovie
import com.kuantum.movieapp.util.Resource
import com.kuantum.movieapp.viewmodel.MovieDetailsViewModel
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_movie_details) {

    private var fragmentBinding: FragmentMovieDetailsBinding? = null
    private lateinit var viewModel: MovieDetailsViewModel
    private var selectedMovie : SavedMovie? = null
    private var id: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MovieDetailsViewModel::class.java]

        val binding = FragmentMovieDetailsBinding.bind(view)
        fragmentBinding = binding

        arguments?.let {
            id = MovieDetailsFragmentArgs.fromBundle(it).id
            viewModel.getMovieDetails(id = id)
        }

        binding.btnFavorite.setOnClickListener {
            selectedMovie?.let {movie ->
                println(movie.director)
                viewModel.insertOrDeleteMovie(movie)
                viewModel.setIsFavorite()
            }
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is Resource.Loading -> {
                    println("loading")
                    fragmentBinding!!.txtError.visibility = View.GONE
                    fragmentBinding!!.progressBar.visibility = View.VISIBLE
                    fragmentBinding!!.linearlayout.visibility = View.GONE
                }

                is Resource.Error -> {
                    println("error")
                    fragmentBinding!!.txtError.visibility = View.VISIBLE
                    fragmentBinding!!.progressBar.visibility = View.GONE
                    fragmentBinding!!.linearlayout.visibility = View.GONE
                }

                is Resource.Success -> {
                    println("success")

                    it.data?.let { movieDetails ->
                        fragmentBinding!!.txtError.visibility = View.GONE
                        fragmentBinding!!.progressBar.visibility = View.GONE
                        fragmentBinding!!.linearlayout.visibility = View.VISIBLE
                        fragmentBinding!!.txtTitle.text = movieDetails.title
                        fragmentBinding!!.txtYear.text = movieDetails.year
                        fragmentBinding!!.txtDirector.text = "Director: ${movieDetails.director}"
                        fragmentBinding!!.txtActors.text = "Actors: ${movieDetails.actors}"
                        fragmentBinding!!.txtAwards.text = "Awards: ${movieDetails.awards}"
                        fragmentBinding!!.txtRuntime.text = "Runtime: ${movieDetails.runtime}"
                        glide.load(movieDetails.poster).into(fragmentBinding!!.imageviewPoster)

                        selectedMovie = movieDetails.toSavedMovie()
                    }
                }
            }
        })

        viewModel.idList.observe(viewLifecycleOwner, Observer {
            viewModel.checkIsFavorite(id = id, idList = it)
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            if (it) {
                fragmentBinding!!.btnFavorite.visibility = View.VISIBLE
                fragmentBinding!!.btnFavorite.setBackgroundResource(R.drawable.favorite)
            } else {
                fragmentBinding!!.btnFavorite.visibility = View.VISIBLE
                fragmentBinding!!.btnFavorite.setBackgroundResource(R.drawable.favorite_border)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}