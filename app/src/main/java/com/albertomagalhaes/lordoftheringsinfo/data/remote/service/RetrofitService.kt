package com.albertomagalhaes.lordoftheringsinfo.data.remote.service

import com.albertomagalhaes.lordoftheringsinfo.BuildConfig.ACCESS_TOKEN
import com.albertomagalhaes.lordoftheringsinfo.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    fun getInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = BasicAuthInterceptor(ACCESS_TOKEN)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Returns the declared interface type as a service
     */
    inline fun <reified T> getService(): T {
        return getInstance().create(T::class.java)
    }

}