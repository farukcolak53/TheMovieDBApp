package com.example.themoviedbapp.ui.favourite

import android.R.animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedbapp.data.remote.Movie
import com.example.themoviedbapp.databinding.FragmentFavouriteBinding
import com.example.themoviedbapp.toMovieList
import com.example.themoviedbapp.ui.movie.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), FavouriteMovieAdapter.OnItemClickListener {
    private val viewModel by viewModels<FavouriteViewModel>()

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private var layoutManager: GridLayoutManager? = null
    private var adapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = GridLayoutManager(context, 2)
        val adapter = FavouriteMovieAdapter(this, layoutManager)

        binding.apply {
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter
            rvMovie.layoutManager = layoutManager
        }
        viewModel.favouriteMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMovieList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.example.themoviedbapp.R.menu.menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.themoviedbapp.R.id.change_layout -> {
                if (layoutManager?.spanCount == 1) {
                    layoutManager?.spanCount = 2
                    item.title = "GRID"
                } else {
                    layoutManager?.spanCount = 1
                    item.title = "LIST"
                }
                adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(movie: Movie?) {
        val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(movie!!)
        findNavController().navigate(
            action,
            navOptions { // Use the Kotlin DSL for building NavOptions
                anim {
                    enter = animator.fade_in
                    exit = animator.fade_out
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
