package com.elmenus.assignment.menu.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.elmenus.assignment.R
import com.elmenus.assignment.itemDetails.ItemDetailsActivity
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
            menuRecyclerView.adapter = ItemsRecyclerViewAdapter(items) { clickedItem ,clickedCardImage->
                clickedItem?.let { item -> onItemClicked(item,clickedCardImage) }
            }
        })
        viewModel.reloadTags()
    }

    private fun onItemClicked(clickedItem: Item, cardImage: ImageView) {
        val intent = Intent(this, ItemDetailsActivity::class.java)
        intent.putExtra(ItemDetailsActivity.ITEM_PHOTO_KEY, clickedItem.photoURL)
        intent.putExtra(ItemDetailsActivity.ITEM_NAME_KEY, clickedItem.name)
        intent.putExtra(ItemDetailsActivity.ITEM_DESCRIPTION_KEY, clickedItem.description)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, cardImage, "itemMainImage")
        startActivity(intent, options.toBundle())
    }

    private fun onSelectedTagChanged(tag: Tag?) {
        tag?.name?.let { tagName -> viewModel.setSelectedTagByName(tagName) }
    }
}