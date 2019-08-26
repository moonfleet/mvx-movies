package com.moonfleet.movies.base

import android.content.Context
import android.view.View
import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    open fun getProgressView() : View? = null

    fun showHideProgress(showProgress: Boolean) {
        if (showProgress) showProgress() else hideProgress()
    }

    fun showProgress() {
        getProgressView()?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        getProgressView()?.visibility = View.GONE
    }

}