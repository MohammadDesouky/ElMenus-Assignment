package com.elmenus.assignment.main.repository.web

import com.elmenus.assignment.main.model.FoodTagItemsApiResponse
import com.elmenus.assignment.main.model.FoodTagsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuApiCall {

    @GET("/tags/{pageNumber}")
    fun getTags(@Path("pageNumber") pageNumber: Int): Call<FoodTagsApiResponse>

    @GET("/items/{tagName}")
    fun getItemsOfTag(@Path("tagName") tagName: String): Call<FoodTagItemsApiResponse>
}