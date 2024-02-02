package com.kuantum.movieapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.kuantum.movieapp.adapter.FavListRecyclerAdapter
import com.kuantum.movieapp.adapter.MoviesRecyclerAdapter
import com.kuantum.movieapp.repository.MoviesRepository
import javax.inject.Inject


class MoviesFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val moviesAdapter: MoviesRecyclerAdapter,
    private val favListRecyclerAdapter: FavListRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MoviesFragment::class.java.name -> MoviesFragment(moviesAdapter)
            MovieDetailsFragment::class.java.name -> MovieDetailsFragment(glide)
            FavoriteListFragment::class.java.name -> FavoriteListFragment(favListRecyclerAdapter)
            FavMovieDetailsFragment::class.java.name -> FavMovieDetailsFragment(glide)

            else -> super.instantiate(classLoader, className)
        }
    }
}
