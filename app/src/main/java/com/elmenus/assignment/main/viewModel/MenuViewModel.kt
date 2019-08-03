package com.elmenus.assignment.main.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.repository.MenuRepository
import com.elmenus.assignment.main.repository.db.MenuDB

class MenuViewModel(db: MenuDB, application: Application) : AndroidViewModel(application) {

    private val repository = MenuRepository(db,application.applicationContext)

    val observableTags: LiveData<PagedList<FoodTag>> = repository.observableTags
    val observableItemsOfSelectedTags: LiveData<List<Item>> = repository.observableItemsOfSelectedTag

    fun reloadTags() {
        repository.invalidateTags()
    }

    fun setSelectedTagByName(name: String) {
        repository.setSelectedTagByName(name)
    }
}