package com.moonfleet.movies.repo

import androidx.palette.graphics.Palette
import com.bumptech.glide.RequestManager
import com.moonfleet.movies.NetworkResult
import com.moonfleet.movies.api.model.GetMoviesResponse
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.api.service.MovieService
import com.moonfleet.movies.messageOrUnknown
import com.moonfleet.movies.toNetworkResult
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(var movieService: MovieService, var glide: RequestManager): APIRepository {

    // Refactor to Flowable with incremental state updates
    // +onError and onComplete
    override fun getPopularMovies(): Single<NetworkResult> {
        return movieService.getPopular(1)
            .map { it.toNetworkResult() }
            .map { result ->
                when(result) {
                    is NetworkResult.Movies -> {
                        val posters = ArrayList<MoviePoster>()
                        result.payload.forEach { movie ->
                            val futureBitmap = glide.asBitmap().load("https://image.tmdb.org/t/p/w500${movie.posterPath}").submit()
                            val bitmap = futureBitmap.get()
                            posters.add(MoviePoster(movie, Palette.generate(bitmap), bitmap))
                        }
                        NetworkResult.MoviePosters(posters)
                    }
                    else -> result
                }
            }
            .onErrorReturn { error -> NetworkResult.HttpError(900, error.messageOrUnknown()) }
    }

    override fun getUpcomingMovies(): Single<Response<GetMoviesResponse>> = movieService.getPopular(1)

}