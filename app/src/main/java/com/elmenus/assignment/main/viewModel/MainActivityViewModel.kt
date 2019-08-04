package com.elmenus.assignment.main.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.repository.FoodRepository
import com.elmenus.assignment.main.repository.db.FoodDatabase

class MainActivityViewModel(db: FoodDatabase, application: Application) : AndroidViewModel(application) {

    private val repository = FoodRepository(db, application.applicationContext)
    private var lastSelectedTagName = ""
    val observableTags: LiveData<PagedList<FoodTag>> = repository.observableTags
    val observableItemsOfSelectedTags: LiveData<List<Item>> = repository.observableItemsOfSelectedTag

    fun setSelectedTagByName(name: String) {
        lastSelectedTagName = name
        repository.setSelectedTagByName(name)
    }

    fun reloadTags() {
        repository.reloadTags()
    }

    fun reloadSelectedTagItems() {
        if (lastSelectedTagName.isNotEmpty()) {
            setSelectedTagByName(lastSelectedTagName)
        }
    }
}