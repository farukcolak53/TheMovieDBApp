package com.example.themoviedbapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.themoviedbapp.data.local.MovieDbRepository
import com.example.themoviedbapp.data.remote.Movie
import com.example.themoviedbapp.databinding.FragmentDetailBinding
import com.example.themoviedbapp.toMovieEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var repository: MovieDbRepository

    val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            args.movie,
            repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toggleFavorite.setOnClickListener {
            viewModel.addToFavourite((args.movie as Movie).toMovieEntity())
        }
    }
}