package com.elmenus.assignment.menu.repository

import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.FoodTagItemsApiResponse
import com.elmenus.assignment.menu.repository.data.FoodTagDataSourceFactory
import com.elmenus.assignment.menu.repository.db.MenuDB
import com.elmenus.assignment.menu.repository.db.TagsBoundaryCallBack
import com.elmenus.assignment.menu.repository.web.MenuApiCall
import com.elmenus.assignment.utils.RetrofitCreator
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuRepository(val db: MenuDB, context: Context) {

    private val api = RetrofitCreator.new<MenuApiCall>()
    private val sourceFactory = FoodTagDataSourceFactory()
    private val callback = TagsBoundaryCallBack(context, db, api)

    var observableTags = db.tagsDao().getAllTags().toLiveData(
        pageSize = 8,
        boundaryCallback = callback
    )
    var observableItemsOfSelectedTag = MutableLiveData<List<Item>>()

    fun invalidateTags() {
        sourceFactory.sourceLiveData.value?.invalidate()
    }

    fun setSelectedTagByName(tagName: String) {

        doAsync {
            val cachedItems = db.itemsDao().getAllItemsOf(tagName)
            if (cachedItems.isEmpty()) {
                loadFromApi(tagName)
            } else {
                uiThread {
                    observableItemsOfSelectedTag.value = cachedItems
                }
            }
        }
    }

    private fun loadFromApi(tagName: String) {
        api.getItemsOfTag(tagName).enqueue(object : Callback<FoodTagItemsApiResponse> {

            override fun onResponse(call: Call<FoodTagItemsApiResponse>, response: Response<FoodTagItemsApiResponse>) {
                val items = response.body()?.items
                observableItemsOfSelectedTag.value = items
                doAsync {
                    items?.map { it.tagName = tagName }
                    items?.let { db.itemsDao().insert(it) }
                }
            }

            override fun onFailure(call: Call<FoodTagItemsApiResponse>, t: Throwable) {
                Log.e("","")
            }

        })
    }
}