package com.elmenus.assignment.menu.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.ItemsApiResponse
import com.elmenus.assignment.menu.model.Tag
import com.elmenus.assignment.menu.model.TagsApiResponse
import com.elmenus.assignment.menu.repository.data.TagDataSourceFactory
import com.elmenus.assignment.menu.repository.db.MenuDB
import com.elmenus.assignment.menu.repository.web.MenuApiCall
import com.elmenus.assignment.utils.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuRepository {

    private val api = RetrofitCreator.new<MenuApiCall>()
    private val sourceFactory = TagDataSourceFactory()
    //private val db= MenuDB.create()
    var observableTags = sourceFactory.toLiveData(8)
    var observableItemsOfSelectedTag = MutableLiveData<ArrayList<Item>>()


    fun invalidateTags() {
        sourceFactory.sourceLiveData.value?.invalidate()
    }

    fun setSelectedTagByName(tagName: String) {
        api.getItemsOfTag(tagName).enqueue(object : Callback<ItemsApiResponse> {

            override fun onResponse(call: Call<ItemsApiResponse>, response: Response<ItemsApiResponse>) {
                observableItemsOfSelectedTag.value = response.body()?.items
            }

            override fun onFailure(call: Call<ItemsApiResponse>, t: Throwable) {

            }

        })
    }
}