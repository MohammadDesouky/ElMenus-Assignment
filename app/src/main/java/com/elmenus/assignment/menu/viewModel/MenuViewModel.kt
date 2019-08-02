package com.elmenus.assignment.menu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.FoodTag
import com.elmenus.assignment.menu.repository.MenuRepository
import com.elmenus.assignment.menu.repository.db.MenuDB

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