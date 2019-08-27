package com.moonfleet.movies.repo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.palette.graphics.Palette
import com.bumptech.glide.RequestManager
import com.moonfleet.movies.*
import com.moonfleet.movies.api.model.Genre
import com.moonfleet.movies.api.model.GetMoviesResponse
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.api.service.MovieService
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class DataRepository @Inject constructor(var movieService: MovieService, var glide: RequestManager): APIRepository {

    private val genresCache: List<Genre> = listOf()

    override fun getGenres(): Flowable<NetworkResult> {
        return if (genresCache.isEmpty()) {
            movieService.getGenres().map { it.toGenresResult() }
        } else {
            Flowable.just(NetworkResult.Genres(genresCache))
        }
    }

    override fun getPopularMovies(): Flowable<MoviePoster> {
        return movieService.getPopular(1)
            .map { it.toMoviesResult() }
            .flatMap { result ->
                when (result) {
                    is NetworkResult.Movies -> Flowable.just(result.payload)
                    is NetworkResult.HttpError -> Flowable.error(RuntimeException("${result.code}: ${result.message}"))
                    else -> Flowable.error(RuntimeException("Type incompatible"))
                }
            }
            .zipWith(getGenres(), BiFunction { movies: List<Movie>, result: NetworkResult ->
                when(result) {
                    is NetworkResult.Genres -> {
                        val posters = ArrayList<MoviePoster>()
                        movies.forEach { movie ->
                            posters.add(MoviePoster(movie = movie, genres = movie.genreIds.map { id -> result.payload.find { genre -> genre.id == id }?.name ?: "Genre" }))
                        }
                        NetworkResult.MoviePosters(posters)
                    }
                    else -> result
                }
            })
            .flatMap { result ->
                when(result) {
                    is NetworkResult.MoviePosters -> Flowable.fromIterable(result.payload)
                    is NetworkResult.HttpError -> Flowable.error(RuntimeException("${result.code}: ${result.message}"))
                    else -> Flowable.error(RuntimeException("Type incompatible"))
                }
            }.map { poster ->
                val bitmap = getBitmapFromURL("https://image.tmdb.org/t/p/w500${poster.movie.posterPath}")
                bitmap?.let {
                    poster.copy(palette = Palette.generate(it), bitmap = it)
                } ?: poster
            }
    }

    override fun getUpcomingMovies(): Flowable<MoviePoster> = getPopularMovies()

    private fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val connection = URL(src).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            return BitmapFactory.decodeStream(connection.inputStream)
        } catch (e: IOException) {
            return null
        }
    }

}