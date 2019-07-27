package com.elmenus.assignment.menu.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.elmenus.assignment.R
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.Tag
import com.elmenus.assignment.menu.viewModel.MenuViewModel
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        viewModel = ViewModelProviders.of(this)[MenuViewModel::class.java]
        val adapter = TagsPagedAdapter { tag ->
            onSelectedTagChanged(tag)
        }
        tagsRecyclerView.adapter = adapter
        viewModel.observableTags.observe(this, Observer { tagsPagedList ->
            adapter.submitList(tagsPagedList)
            try {
                onSelectedTagChanged(tagsPagedList[0])
            } catch (ignored: Exception) {
            }
        })
        viewModel.observableItemsOfSelectedTags.observe(this, Observer { items ->
            menuRecyclerView.adapter = ItemsRecyclerViewAdapter(items) { clickedItem ->
                clickedItem?.let { item -> onItemClicked(item) }
            }
        })
        viewModel.reloadTags()
    }

    private fun onItemClicked(clickedItem: Item) {

    }

    private fun onSelectedTagChanged(tag: Tag?) {
        tag?.name?.let { tagName -> viewModel.setSelectedTagByName(tagName) }
    }
}