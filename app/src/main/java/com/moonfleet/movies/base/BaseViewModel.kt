package com.moonfleet.movies.base

import androidx.lifecycle.ViewModel
import com.moonfleet.movies.util.BaseSchedulerProvider
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(schedulerProvider: BaseSchedulerProvider) : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val subscribeOnScheduler: Scheduler
    val observeOnScheduler: Scheduler

    init {
        subscribeOnScheduler = schedulerProvider.io()
        observeOnScheduler = schedulerProvider.ui()
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun <T> Single<T>.safeSubscribe(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit = {}) : Disposable {
        val disposable = scheduleAsync().subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
        return disposable
    }

    fun <T> Observable<T>.safeSubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit = {}, onComplete: () -> Unit = {}) : Disposable {
        val disposable = scheduleAsync().subscribe(onNext, onError, onComplete)
        compositeDisposable.add(disposable)
        return disposable
    }

    fun <T> Flowable<T>.safeSubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit = {}, onComplete: () -> Unit = {}) : Disposable {
        val disposable = scheduleAsync().subscribe(onNext, onError, onComplete)
        compositeDisposable.add(disposable)
        return disposable
    }

    fun Completable.safeSubscribe(onSuccess: () -> Unit, onError: (Throwable) -> Unit = {}) : Disposable {
        val disposable = scheduleAsync().subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
        return disposable
    }

    private fun <T> schedule(emitter: Single<T>) = emitter.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    private fun <T> schedule(emitter: Observable<T>) = emitter.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    private fun <T> schedule(emitter: Flowable<T>) = emitter.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    private fun schedule(emitter: Completable) = emitter.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)


    fun <T> Single<T>.scheduleAsync(): Single<T> = this.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    fun <T> Observable<T>.scheduleAsync(): Observable<T> = this.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    fun <T> Flowable<T>.scheduleAsync(): Flowable<T> = this.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    fun Completable.scheduleAsync(): Completable = this.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)

}