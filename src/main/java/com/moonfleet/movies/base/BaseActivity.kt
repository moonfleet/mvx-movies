package com.moonfleet.movies.base

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.moonfleet.movies.R
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    public lateinit var navigator: BaseNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        navigator.activity = null
    }

    override fun onResume() {
        super.onResume()
        navigator.activity = this
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

    fun showMessage(s: String) {
        showMessage(s, { materialDialog -> materialDialog.dismiss() })
    }

    private fun showMessage(s: String, onPositive: (MaterialDialog) -> Unit) {
        MaterialDialog(this).show {
            message(text = s)
            positiveButton(R.string.close, click = onPositive)
            noAutoDismiss()
        }
    }

}