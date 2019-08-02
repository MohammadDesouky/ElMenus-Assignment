package com.elmenus.assignment.menu.repository.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.elmenus.assignment.menu.model.FoodTag

class FoodTagDataSourceFactory : DataSource.Factory<Int, FoodTag>() {

    val sourceLiveData = MutableLiveData<FoodTagDataSource>()
    lateinit var latestSource: FoodTagDataSource

    override fun create(): DataSource<Int, FoodTag> {
        latestSource = FoodTagDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }

}