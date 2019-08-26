package com.moonfleet.movies.features.list.activity

import android.os.Bundle
import androidx.navigation.findNavController
import com.moonfleet.movies.R
import com.moonfleet.movies.base.BaseActivity
import com.moonfleet.movies.features.list.fragment.MoviesListFragment
import kotlinx.android.synthetic.main.activity_container.*

class MoviesActivity : BaseActivity(), MoviesListFragment.MoviesListFragmentHost {

    override fun onMessage(message: String) = showMessage(message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        initViews()
    }

    private fun initViews() {
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()

}