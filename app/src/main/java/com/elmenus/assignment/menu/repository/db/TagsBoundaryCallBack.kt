package com.elmenus.assignment.menu.repository.db

import android.content.Context
import androidx.paging.PagedList
import com.elmenus.assignment.menu.model.FoodTag
import com.elmenus.assignment.menu.model.FoodTagsApiResponse
import com.elmenus.assignment.menu.repository.web.MenuApiCall
import com.elmenus.assignment.utils.PreferenceManager
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TagsBoundaryCallBack(
    val context: Context,
    val db: MenuDB,
    val api: MenuApiCall
) :
    PagedList.BoundaryCallback<FoodTag>() {


    companion object {
        private const val LAST_FETCHED_PAGE_FROM_API_SHARED_PREF_KEY = "LAST_FETCHED_PAGE_FROM_API_SHARED_PREF_KEY"
    }

    private var lastLoadedPage = 1

    init {
        lastLoadedPage = PreferenceManager.getInt(context, LAST_FETCHED_PAGE_FROM_API_SHARED_PREF_KEY, 1)
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        loadTags(1)
    }

    override fun onItemAtEndLoaded(itemAtEnd: FoodTag) {
        super.onItemAtEndLoaded(itemAtEnd)
        loadTags(++lastLoadedPage)
    }

    private fun loadTags(pageNumber: Int) {
        api.getTags(pageNumber).enqueue(getApiCallback(pageNumber))

    }

    private fun saveLastLoadedPageNumber(pageNumber: Int) {
        PreferenceManager.setInt(context, LAST_FETCHED_PAGE_FROM_API_SHARED_PREF_KEY, pageNumber)
    }

    private fun getApiCallback(requestedPageNumber: Int): Callback<FoodTagsApiResponse> {

        return object : Callback<FoodTagsApiResponse> {

            override fun onResponse(call: Call<FoodTagsApiResponse>, response: Response<FoodTagsApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.foodTags?.let { foodTags ->
                        doAsync {
                            if (foodTags.isNotEmpty()) {
                                db.tagsDao().insert(foodTags)
                            }
                        }
                        saveLastLoadedPageNumber(requestedPageNumber)
                    }
                }

            }

            override fun onFailure(call: Call<FoodTagsApiResponse>, t: Throwable) {
            }
        }
    }
}

