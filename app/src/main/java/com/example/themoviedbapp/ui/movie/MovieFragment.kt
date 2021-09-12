package com.example.themoviedbapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedbapp.R
import com.example.themoviedbapp.data.remote.Movie
import com.example.themoviedbapp.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieAdapter.OnItemClickListener {
    private val viewModel by viewModels<MovieViewModel>()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private var layoutManager: GridLayoutManager? = null
    private var adapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = GridLayoutManager(context, 2)
        val adapter = MovieAdapter(this, layoutManager)

        binding.apply {
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter
            rvMovie.layoutManager = layoutManager
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searhQuery: String?): Boolean {
                if (searhQuery != null) {
                    binding.rvMovie.scrollToPosition(0)
                    viewModel.searchPhotos(searhQuery)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.change_layout -> {
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
        val action = MovieFragmentDirections.actionMovieFragmentToDetailFragment(movie!!)
        findNavController().navigate(
            action,
            navOptions { // Use the Kotlin DSL for building NavOptions
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
