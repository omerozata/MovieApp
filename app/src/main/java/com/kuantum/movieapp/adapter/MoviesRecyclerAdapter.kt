package com.kuantum.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kuantum.movieapp.databinding.MoviesItemBinding
import com.kuantum.movieapp.model.Movie
import javax.inject.Inject

class MoviesRecyclerAdapter @Inject constructor(
    private val glide : RequestManager
) : RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesRecyclerHolder>() {

    class MoviesRecyclerHolder(val binding: MoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var onItemClick: ((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieList: List<Movie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRecyclerHolder {
        val binding = MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesRecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MoviesRecyclerHolder, position: Int) {
        val movie = movieList[position]

        holder.binding.txtMovieName.text = movie.title
        holder.binding.txtYear.text = movie.year
        glide.load(movie.poster).into(holder.binding.imageviewPoster)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(movie.imdbID)
        }
    }
}