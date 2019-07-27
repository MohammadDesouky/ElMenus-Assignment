package com.elmenus.assignment.menu.repository.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.elmenus.assignment.menu.model.Tag

class TagDataSourceFactory : DataSource.Factory<Int, Tag>() {

    val sourceLiveData = MutableLiveData<TagDataSource>()
    lateinit var latestSource: TagDataSource

    override fun create(): DataSource<Int, Tag> {
        latestSource = TagDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }

}