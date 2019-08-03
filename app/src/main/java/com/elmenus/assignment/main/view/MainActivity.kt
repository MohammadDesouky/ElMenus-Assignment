package com.elmenus.assignment.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.elmenus.assignment.R
import com.elmenus.assignment.itemDetails.ItemDetailsActivity
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.main.repository.db.MenuDB
import com.elmenus.assignment.main.viewModel.MenuViewModel
import kotlinx.android.synthetic.main.activity_menu.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var adapter: FoodTagsPagedAdapter
    private var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        viewModel = getViewModel()
        updateTagsAdapter()
        observeTagsChanges()
        observeItemsOfTagChanges()
        reloadTags()
    }

    private fun reloadTags() {
        viewModel.reloadTags()
    }

    private fun observeItemsOfTagChanges() {
        viewModel.observableItemsOfSelectedTags.observe(this, Observer { items ->
            hideItemsLoader()
            menuRecyclerView.adapter =
                ItemsRecyclerViewAdapter(items as ArrayList<Item>) { clickedItem, clickedCardImage ->
                    clickedItem?.let { item -> onItemClicked(item, clickedCardImage) }
                }
        })
    }

    private fun observeTagsChanges() {
        viewModel.observableTags.observe(this, Observer { tagsPagedList ->
            adapter.submitList(tagsPagedList)
            try{
                if (firstLoad) {
                    onSelectedTagChanged(0, tagsPagedList[0])
                    firstLoad = false
                }
            }
            catch(ignored:Exception){}
        })
    }

    private fun updateTagsAdapter() {
        adapter = FoodTagsPagedAdapter { tagIndex, tag ->
            onSelectedTagChanged(tagIndex, tag)
            menuRecyclerView?.adapter?.notifyDataSetChanged()
        }
        foodTagsRecyclerView.adapter = adapter
    }

    private fun getViewModel(): MenuViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val database = MenuDB.create(applicationContext)
                @Suppress("UNCHECKED_CAST")
                return MenuViewModel(database, application) as T
            }
        })[MenuViewModel::class.java]
    }

    private fun onItemClicked(clickedItem: Item, cardImage: ImageView) {
        val intent = Intent(this, ItemDetailsActivity::class.java).run {
            putExtra(ItemDetailsActivity.ITEM_PHOTO_KEY, clickedItem.photoURL)
            putExtra(ItemDetailsActivity.ITEM_NAME_KEY, clickedItem.name)
            putExtra(ItemDetailsActivity.ITEM_DESCRIPTION_KEY, clickedItem.description)
        }

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, cardImage, "itemMainImage")
        startActivity(intent, options.toBundle())
    }

    private fun onSelectedTagChanged(selectedTagIndex: Int, tag: FoodTag?) {
        if(adapter.selectedFoodTagIndex!=selectedTagIndex) {
            showItemsLoader()
            tag?.name?.let { tagName -> viewModel.setSelectedTagByName(tagName) }
            adapter.selectedFoodTagIndex = selectedTagIndex
            adapter.notifyDataSetChanged()
        }
    }

    private fun showItemsLoader() {
        menuRecyclerView?.visibility = View.GONE
        menuProgressBar?.visibility = View.VISIBLE
    }

    private fun hideItemsLoader() {
        menuRecyclerView?.visibility = View.VISIBLE
        menuProgressBar?.visibility = View.GONE
    }
}