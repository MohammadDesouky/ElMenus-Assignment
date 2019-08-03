package com.elmenus.assignment.utils

import com.elmenus.assignment.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCreator {

    const val baseURL = "https://elmenus-assignment.getsandbox.com"

    inline fun <reified T> new(): T {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client: OkHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                this.addInterceptor(interceptor)
            }
        }.build()
        return newRetrofitWebService(client, baseURL)
    }

    inline fun <reified T> newRetrofitWebService(client: OkHttpClient, baseURL: String): T {
        val retrofit = Retrofit.Builder().client(client)
        retrofit.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(baseURL)
        return retrofit
            .build()
            .create(T::class.java)
    }

}