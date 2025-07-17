package com.jar.app.data.remote.network

import com.jar.app.BuildConfig
import com.jar.app.data.remote.api.ApiService
import com.jar.app.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    val retrofit : Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object RetrofitGoldApiClient {
    val retrofit : Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(Constants.GOLD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

val apiKey = BuildConfig.GOLD_API_KEY

private val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-access-token", apiKey)
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()
}

object ApiClient{
    val apiService : ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
    val goldService: ApiService by lazy {
        RetrofitGoldApiClient.retrofit.create(ApiService::class.java)
    }
}
