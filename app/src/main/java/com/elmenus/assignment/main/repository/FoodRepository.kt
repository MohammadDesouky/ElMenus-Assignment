package com.elmenus.assignment.main.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.elmenus.assignment.constants.AppConstants
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.main.model.FoodTagItemsApiResponse
import com.elmenus.assignment.main.repository.db.FoodDatabase
import com.elmenus.assignment.main.repository.db.FoodTagsBoundaryCallBack
import com.elmenus.assignment.main.repository.web.ApiCalls
import com.elmenus.assignment.utils.RetrofitCreator
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class FoodRepository(val db: FoodDatabase, context: Context) {

    private val api = RetrofitCreator.new<ApiCalls>()
    private val callback = FoodTagsBoundaryCallBack(context, db, api)

    var observableTags = db.tagsDao().getAllTags().toLiveData(
        pageSize = AppConstants.DATABASE_PAGE_SIZE,
        boundaryCallback = callback,
        fetchExecutor = Executors.newSingleThreadExecutor()
    )
    var observableItemsOfSelectedTag = MutableLiveData<List<Item>>()

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
                observableItemsOfSelectedTag.value = null
            }

        })
    }

    fun reloadTags() {
        callback.onZeroItemsLoaded()
    }

    fun invalidateData() {
        db.clearAllTables()
    }

}