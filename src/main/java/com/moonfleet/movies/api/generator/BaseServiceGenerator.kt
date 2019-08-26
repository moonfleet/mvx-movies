package com.moonfleet.movies.api.generator

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

abstract class BaseServiceGenerator<T> internal constructor() {

    private val retrofit: Retrofit
    private val mockRetrofit: MockRetrofit
    private var service: T? = null
    private var mockService: BehaviorDelegate<T>? = null

    protected val retrofitBuilder: Retrofit.Builder
        get() = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(getBaseURL())
                .client(httpClient)

    protected val gsonBuilder: GsonBuilder
        get() = GsonBuilder().setLenient()

    protected val httpClient: OkHttpClient
        get() = httpClientBuilder.build()

    protected val httpClientBuilder: OkHttpClient.Builder
        get() {
            val requestInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    val url = request.url.newBuilder()
                            .addQueryParameter("api_key", "4bb61a41279eac5465e56411bce5f186").build()
                    request = request.newBuilder()
                            .url(url)
                            .addHeader("Accepts", "application/json")
                            .build()
                    return chain.proceed(request)
                }
            }

            val httpClientBuilder = OkHttpClient.Builder()

            httpClientBuilder
                    .addInterceptor(requestInterceptor)
                    .readTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS)
                    .connectTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS)

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)

            return httpClientBuilder
        }

    init {
        retrofit = retrofitBuilder.build()
        mockRetrofit = buildMockRetrofit(retrofit)
    }

    fun getMockService(cls: Class<T>): BehaviorDelegate<T> {
        if (mockService == null) {
            mockService = mockRetrofit.create(cls)
        }
        return mockService!!
    }

    protected fun getService(cls: Class<T>): T {
        if (service == null) {
            service = retrofit.create(cls)
        }
        return service!!
    }

    protected fun buildMockRetrofit(retrofit: Retrofit): MockRetrofit {
        val networkBehavior = NetworkBehavior.create()
        networkBehavior.setDelay(TEST_DELAY_SHORT, TimeUnit.MILLISECONDS)
        networkBehavior.setVariancePercent(TEST_DELAY_VARIANCE_PERCENT)
        networkBehavior.setFailurePercent(TEST_FAILURE_PERCENT)

        return MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior)
                .build()
    }

    protected fun getBaseURL(): String {
        return "https://api.themoviedb.org/3/"
    }

    companion object {
        val DELAY_NONE = 0L
        val TIME_OUT_MILLIS = 60000L
        val TEST_DELAY_SHORT = 3000L
        val TEST_DELAY_VARIANCE_PERCENT = 50
        val TEST_FAILURE_PERCENT = 0
    }

}