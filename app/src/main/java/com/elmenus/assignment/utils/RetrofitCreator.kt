package com.elmenus.assignment.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCreator {

    const val baseURL = "https://elmenus-assignment.getsandbox.com"

    inline fun <reified T> new(): T {
        val client =  OkHttpClient().newBuilder()
        return newRetrofitWebService(client, baseURL)
    }

    inline fun <reified T> newRetrofitWebService(client: OkHttpClient.Builder, baseURL: String): T {
        val retrofit = Retrofit.Builder().client(client.build())
        retrofit.addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).baseUrl(baseURL)
        return retrofit.build().create(T::class.java)
    }

}