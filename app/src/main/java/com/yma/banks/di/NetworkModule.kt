package com.yma.banks.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yma.banks.BuildConfig
import com.yma.banks.api.PersonApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val MODULE_NAME = "Network Module"

val networkModule = Kodein.Module(MODULE_NAME, false) {
    bind<OkHttpClient>() with singleton { getOkHttpClient() }
    bind<Retrofit>() with singleton { getRetrofit(instance()) }
    bind<PersonApiService>() with singleton { getBanksApiService(instance()) }
}

val gson: Gson = GsonBuilder().setLenient().create()

private fun getOkHttpClient(): OkHttpClient {
    val httpBuilder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    if (BuildConfig.ENABLE_LOGGING) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpBuilder.interceptors()
            .add(httpLoggingInterceptor)
    }

    httpBuilder.addInterceptor(object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()

            val request: Request = original.newBuilder()
                .header("app-id", "5ff330809f8b8c42d508a650")
                .method(original.method(), original.body())
                .build()

            return chain.proceed(request)
        }

    })

    return httpBuilder.build()
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(okHttpClient)
    .build()

private fun getBanksApiService(retrofit: Retrofit): PersonApiService =
    retrofit.create(PersonApiService::class.java)
