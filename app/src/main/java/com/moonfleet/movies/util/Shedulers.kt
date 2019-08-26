package com.moonfleet.movies.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

interface BaseSchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}

class UISchedulerProvider @Inject constructor() : BaseSchedulerProvider {
    override fun ui() = AndroidSchedulers.mainThread()
    override fun io() = Schedulers.io()
}

class TestSchedulerProvider(private val testScheduler: TestScheduler) : BaseSchedulerProvider {
    override fun ui() = testScheduler
    override fun io() = testScheduler
}
class TrampolineSchedulerProvider() : BaseSchedulerProvider {
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
}