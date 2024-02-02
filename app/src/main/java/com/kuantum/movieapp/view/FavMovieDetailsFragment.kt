package com.kuantum.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.kuantum.movieapp.R
import com.kuantum.movieapp.databinding.FragmentFavMovieDetailsBinding
import com.kuantum.movieapp.model.SavedMovie
import com.kuantum.movieapp.viewmodel.FavMovieDetailsViewModel
import javax.inject.Inject

class FavMovieDetailsFragment @Inject constructor(
    private val glide : RequestManager
) : Fragment(R.layout.fragment_fav_movie_details) {

    private var fragmentBinding : FragmentFavMovieDetailsBinding? = null
    private lateinit var viewModel : FavMovieDetailsViewModel
    private var id = ""
    private var currentMovie : SavedMovie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[FavMovieDetailsViewModel::class.java]

        val binding = FragmentFavMovieDetailsBinding.bind(view)
        fragmentBinding = binding

        arguments?.let {
            id = FavMovieDetailsFragmentArgs.fromBundle(it).id
            viewModel.getMovie(id)
        }

        binding.btnFavorite.setOnClickListener {
            currentMovie?.let {
                viewModel.insertOrDeleteMovie(it)
                viewModel.setIsFavorite()
            }

        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.movie.observe(viewLifecycleOwner, Observer{movie ->
            if (movie == null)
                return@Observer

            currentMovie = movie
            viewModel.setIsFavorite(true)

            fragmentBinding?.let {
                it.txtTitle.text = movie.title
                it.txtYear.text = movie.year
                it.txtRuntime.text = movie.runtime
                it.txtDirector.text = movie.director
                it.txtAwards.text = movie.awards
                it.txtActors.text = movie.actors
                it.btnFavorite.visibility = View.VISIBLE
                glide.load(movie.poster).into(it.imageviewPoster)
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            if (it) {
                println(it)
                fragmentBinding!!.btnFavorite.setBackgroundResource(R.drawable.favorite)
            } else {
                println(it)
                fragmentBinding!!.btnFavorite.setBackgroundResource(R.drawable.favorite_border)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}