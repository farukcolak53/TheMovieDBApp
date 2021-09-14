package com.example.themoviedbapp.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.themoviedbapp.R
import com.example.themoviedbapp.data.remote.Movie
import com.example.themoviedbapp.databinding.ItemMovieBinding
import com.example.themoviedbapp.databinding.ItemMovieListBinding

class MovieAdapter(
    private val listener: OnItemClickListener,
    private val layoutManager: GridLayoutManager? = null
) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    enum class ViewType {
        LIST,
        GRID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.GRID.ordinal -> {
                val binding =
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItemViewType(position)
        if (currentItem == ViewType.GRID.ordinal &&
            holder is GridViewHolder
        ) {
            val currentItemMovie = getItem(position)
            if (currentItemMovie != null) holder.bind(currentItemMovie)
        } else if (currentItem == ViewType.LIST.ordinal &&
            holder is ListViewHolder
        ) {
            val currentItemMovie = getItem(position)
            if (currentItemMovie != null) holder.bind(currentItemMovie)
        }
    }

    inner class ListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            binding.titleText.text = movie.title
        }

        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = getItem(position)
                listener.onItemClick(item)
            }
        }
    }

    inner class GridViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView)
                    .load("${movie.baseUrl}${movie.posterPath}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                titleText.text = movie.originalTitle
            }
        }

        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = getItem(position)
                listener.onItemClick(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager?.spanCount == 2) ViewType.GRID.ordinal
        else ViewType.LIST.ordinal
    }

    // Adapter uses this interface to send a click event to the fragment
    interface OnItemClickListener {
        fun onItemClick(movie: Movie?)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
