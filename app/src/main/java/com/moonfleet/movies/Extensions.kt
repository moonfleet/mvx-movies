package com.moonfleet.movies

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.moonfleet.movies.api.model.GetMoviesResponse
import com.moonfleet.movies.api.model.Movie
import com.moonfleet.movies.api.model.MoviePoster
import com.moonfleet.movies.base.BaseViewModelFactory
import retrofit2.Response

sealed class NetworkResult {
    data class MoviePosters(val payload: List<MoviePoster>) : NetworkResult()
    data class Movies(val payload: List<Movie>) : NetworkResult()
    data class HttpError(val code: Int, val message: String) : NetworkResult()
}

fun Response<GetMoviesResponse>.toNetworkResult(): NetworkResult =
    if (this.isSuccessful && this.body() != null) {
        NetworkResult.Movies(this.body()!!.movies)
    } else {
        NetworkResult.HttpError(this.code(), this.message())
    }

fun <T> MutableLiveData<T>.startWith(initialValue: T) = apply { value = initialValue }

fun Throwable.messageOrUnknown() = this.message ?: "Unknown error"

// Replaced with lazyViewModel
inline fun <reified T : ViewModel> Fragment.getViewModel(): T = ViewModelProvider(this).get(T::class.java)
// Replaced with lazyViewModel
inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(): T = ViewModelProvider(this).get(T::class.java)

// Used instead of DaggerAwareViewModelFactory inside a view (Activity or Fragment) in a lazy init of a viewModel.
// Advantage is to be able to pass in an instance of the ViewModel created arbitrarily via a constructor, not using a Factory.
// The instance requires a public VM constructor, where all the dependencies are listed.
// It is possible to have a single empty constructor, injecting all the dependencies in the init{} section of each VM,
// but then the VM cannot be created in unit tests.
// Disadvantages are that the view has to inject VM dependencies into itself first, before using them in the creator as described above.
inline fun <reified T : ViewModel> ViewModelStoreOwner.lazyViewModel(noinline creator: (() -> T)? = null) = ViewModelsLazy(this, creator, T::class.java)

class ViewModelsLazy<VM : ViewModel>(private val viewModelStoreOwner: ViewModelStoreOwner, private val creator: (() -> VM)? = null, private val cls: Class<VM>) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            return if (creator == null) {
                ViewModelProvider(viewModelStoreOwner).get(cls)
            } else {
                ViewModelProvider(viewModelStoreOwner, BaseViewModelFactory(creator)).get(cls)
            }
        }

    override fun isInitialized() = cached != null

}