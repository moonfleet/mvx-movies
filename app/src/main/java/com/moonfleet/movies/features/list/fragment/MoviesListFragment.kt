package com.moonfleet.movies.features.list.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moonfleet.movies.R
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.BaseFragment
import com.moonfleet.movies.features.list.state.MoviesListViewState
import com.moonfleet.movies.features.list.viewmodel.MoviesListViewModel
import com.moonfleet.movies.view.DividerItemDecoration
import com.moonfleet.movies.view.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.view.*
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.item_movie_poster.view.*
import timber.log.Timber
import javax.inject.Inject

class MoviesListFragment : BaseFragment() {

    @Inject
    public lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MoviesListViewModel
    lateinit var adapter: MoviesAdapter<MoviePoster>
    var host: MoviesListFragmentHost? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initStateObserver()
        Timber.e("onActivityCreated")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_movies_list, container, false)
        initViews(layout)
        return layout
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as MoviesListFragmentHost
    }

    override fun onDetach() {
        super.onDetach()
        host = null
    }

    private fun initViews(view: View) {
        initRecyclerView(view)
    }

    private fun initRecyclerView(layout: View) {
        with(resources.getInteger(R.integer.movies_recycler_view_columns)) {
            val layoutManager = GridLayoutManager(activity!!.applicationContext, this, RecyclerView.VERTICAL, false)
            layout.recycler_view_movies.addItemDecoration(DividerItemDecoration(this, 32))
            layout.recycler_view_movies.setLayoutManager(layoutManager)
        }
        adapter = MoviesAdapter(listOf(), R.layout.item_movie_poster,
                { _, view, poster ->
                    view.item_movie_tv_title.text = poster.movie.title
                    view.item_movie_iv_poster.setImageBitmap(poster.bitmap)
                    view.item_movie_container_details.setBackgroundColor(poster.palette.getVibrantColor(Color.WHITE))
                    view.item_movie_tv_genre.text = "Subtitle"
                    view
                }, { poster -> viewModel.onMovieClick(poster)})
        layout.recycler_view_movies.setAdapter(adapter)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(MoviesListViewModel::class.java)
    }

    private fun initStateObserver() {
        viewModel.stateLiveData.observe(this, getStateObserver())
    }

    private fun getStateObserver(): Observer<in MoviesListViewState> = Observer { state ->
        Timber.e("onState")
        showHideProgress(state.loading)
        if (state.error != null) {
            host?.onMessage(state.error)
        } else if (state.movies != null) {
            updateList(state.movies)
        } else if (!state.loading) {
            viewModel.onEmptyState()
        }
    }

    private fun updateList(movies: List<MoviePoster>) {
        adapter.updateItems(movies)
    }

    override fun getProgressView(): View? = layout_progress_loading

    companion object {
        fun newInstance(): MoviesListFragment = MoviesListFragment()
    }

    interface MoviesListFragmentHost {
        fun onMessage(message: String)
    }

}