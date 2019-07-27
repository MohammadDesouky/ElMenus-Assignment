package com.elmenus.assignment.menu.repository.data

import androidx.paging.PageKeyedDataSource
import com.elmenus.assignment.menu.model.Tag
import com.elmenus.assignment.menu.repository.web.MenuApiCall
import com.elmenus.assignment.utils.RetrofitCreator
import java.io.IOException

class TagDataSource : PageKeyedDataSource<Int, Tag>() {

    private val api = RetrofitCreator.new<MenuApiCall>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Tag>) {
        try {
            val response = api.getTags(1).execute()
            val data = response.body()
            val items = data?.menuTags ?: arrayListOf()
            callback.onResult(items, 0, 2)
        } catch (ioException: IOException) {
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Tag>) {
        try {
            val response = api.getTags(params.key).execute()
            val data = response.body()
            val items = data?.menuTags ?: arrayListOf()
            callback.onResult(items, params.key + 1)
        } catch (ioException: IOException) {
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tag>) {

    }
}