package com.moonfleet.movies.base

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.moonfleet.movies.R

open class BaseNavigator {

    var activity: BaseActivity? = null

    infix fun NavController?.to(navDirections: NavDirections) {
        this?.navigate(navDirections)
    }

    fun navController() = activity?.findNavController(R.id.nav_host_fragment)

}