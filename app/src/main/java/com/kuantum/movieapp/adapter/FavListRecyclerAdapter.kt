package com.kuantum.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kuantum.movieapp.databinding.FavListItemBinding
import com.kuantum.movieapp.model.SavedMovie
import javax.inject.Inject

class FavListRecyclerAdapter @Inject constructor(
    private val glide: RequestManager
) :
    RecyclerView.Adapter<FavListRecyclerAdapter.FavListRecyclerHolder>() {

    class FavListRecyclerHolder(val binding: FavListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var onItemClick: ((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<SavedMovie>() {
        override fun areItemsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var favList: List<SavedMovie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavListRecyclerHolder {
        val binding = FavListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavListRecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavListRecyclerHolder, position: Int) {
        val movie = favList[position]

        holder.binding.txtMovieName.text = movie.title
        holder.binding.txtYear.text = movie.year
        glide.load(movie.poster).into(holder.binding.imageviewPoster)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(movie.imdbID)
        }
    }
}