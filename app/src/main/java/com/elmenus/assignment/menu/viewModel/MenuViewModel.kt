package com.elmenus.assignment.menu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.Tag
import com.elmenus.assignment.menu.model.TagsApiResponse
import com.elmenus.assignment.menu.repository.MenuRepository

class MenuViewModel : ViewModel() {

    private val repository = MenuRepository()

    val observableTags: LiveData<PagedList<Tag>> = repository.observableTags
    val observableItemsOfSelectedTags: LiveData<ArrayList<Item>> = repository.observableItemsOfSelectedTag

    fun reloadTags() {
        repository.invalidateTags()
    }

    fun setSelectedTagByName(name: String) {
        repository.setSelectedTagByName(name)
    }
}