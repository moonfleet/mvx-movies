package com.moonfleet.movies.api.model

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

data class MoviePoster(val movie: Movie, val palette: Palette? = null, val bitmap: Bitmap? = null, val genres: List<String> = listOf())