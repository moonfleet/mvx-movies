package com.moonfleet.movies.features.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.moonfleet.movies.R
import com.moonfleet.movies.base.BaseFragment
import com.moonfleet.movies.databinding.FragmentMovieDetailsBinding
import com.moonfleet.movies.features.details.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    @Inject
    public lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MovieDetailsViewModel

    val args: MovieDetailsFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViewModel()
        val binding: FragmentMovieDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val view = binding.root
        initViews(view)
        return view
    }

    private fun initViews(layout: View) {
        viewModel.onEmptyState(args.movie.title, args.movie.overview)
        with(layout.text_overview) {
            setOnClickListener { _ -> viewModel.onCloseClick() }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(MovieDetailsViewModel::class.java)
    }

}