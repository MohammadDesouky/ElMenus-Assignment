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
import com.elmenus.assignment.itemDetails.ItemDetailsActivity
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.main.repository.db.FoodDatabase
import com.elmenus.assignment.main.view.foodItemsRecycler.FoodItemsRecyclerViewAdapter
import com.elmenus.assignment.main.view.foodTagRecycler.FoodTagsPagedAdapter
import com.elmenus.assignment.main.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.content.IntentFilter
import com.elmenus.assignment.R
import com.elmenus.assignment.broadcastReceivers.ConnectionState
import com.elmenus.assignment.broadcastReceivers.ConnectionStateBroadCastReceiver
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: FoodTagsPagedAdapter
    private var isFirstLoad = true
    private val connectionStateReceiver = ConnectionStateBroadCastReceiver()
    private lateinit var offlineSnackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = getViewModel()
        updateTagsAdapter()
        observeTagsChanges()
        observeItemsOfTagChanges()
        registerConnectionStateReceiver()
        observeConnectionStateChanges()
        showTagsLoader()
        showItemsLoader()
        createSnackBar()
    }

    private fun createSnackBar() {
        offlineSnackBar =
            Snackbar.make(coordinatorLayout, getString(R.string.no_up_to_date), Snackbar.LENGTH_INDEFINITE)
    }

    override fun onStart() {
        super.onStart()
        doAsync {
            sleep(5000)
            connectionStateReceiver.refreshState(this@MainActivity)
        }
    }

    private fun observeConnectionStateChanges() {
        ConnectionStateBroadCastReceiver.connectionState.observe(this, Observer { connectionState ->
            if (connectionState == ConnectionState.Offline) {
                onGoingOffline()
            } else {
                onBeingOnline()
            }
        })
    }

    private fun onBeingOnline() {
        if (offlineSnackBar.isShown) {
            offlineSnackBar.dismiss()
        }
        if (!alreadyHaveData()) {
            isFirstLoad = true
            showItemsLoader()
            showTagsLoader()
            reloadTags()
        }
    }

    private fun reloadTags() {
        viewModel.reloadTags()
    }

    private fun onGoingOffline() {
        if (alreadyHaveData()) {
            showOfflineSnackBar()
        } else {
            showOfflineMessage()
        }

    }

    private fun showOfflineMessage() {
        tagItemsRecyclerView?.visibility = View.GONE
        menuExtrasContainer?.visibility = View.VISIBLE
        offlineMessageContainer?.visibility = View.VISIBLE
        foodTagsRecyclerView.visibility = View.GONE
        tagsProgressBar.visibility = View.GONE
    }

    private fun showOfflineSnackBar() {
        offlineSnackBar.show()
    }

    private fun alreadyHaveData(): Boolean {
        return adapter.itemCount > 0 && tagItemsRecyclerView?.adapter?.itemCount ?: 0 > 0
    }

    private fun registerConnectionStateReceiver() {
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(connectionStateReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectionStateReceiver)
    }

    private fun observeItemsOfTagChanges() {
        viewModel.observableItemsOfSelectedTags.observe(this, Observer { items ->
            if (items == null) {
                connectionStateReceiver.refreshState(this@MainActivity)
                showItemsLoadingError()
            } else {
                displayTagItems(items)
            }
        })
    }

    private fun displayTagItems(items: List<Item>) {
        showSelectedTagItems()
        tagItemsRecyclerView.adapter =
            FoodItemsRecyclerViewAdapter(items as ArrayList<Item>) { clickedItem, clickedCardImage ->
                clickedItem?.let { item -> onItemClicked(item, clickedCardImage) }
            }
    }

    private fun observeTagsChanges() {
        viewModel.observableTags.observe(this, Observer { tagsPagedList ->
            adapter.submitList(tagsPagedList)
            try {
                if (isFirstLoad) {
                    showFoodTags()
                    onSelectedTagChanged(0, tagsPagedList[0])
                    isFirstLoad = false
                }
            } catch (ignored: Exception) {
            }
        })
    }

    private fun updateTagsAdapter() {
        adapter = FoodTagsPagedAdapter { tagIndex, tag ->
            onSelectedTagChanged(tagIndex, tag)
            tagItemsRecyclerView?.adapter?.notifyDataSetChanged()
        }
        foodTagsRecyclerView.adapter = adapter
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val database = FoodDatabase.create(applicationContext)
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(database, application) as T
            }
        })[MainActivityViewModel::class.java]
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
        if (!isAlreadySelected(selectedTagIndex)) {
            adapter.selectedFoodTagIndex = selectedTagIndex
            adapter.notifyDataSetChanged()
            showItemsLoader()
            tag?.name?.let { tagName -> viewModel.setSelectedTagByName(tagName) }
        }
    }

    private fun reloadSelectedTagItems() {
        viewModel.reloadSelectedTagItems()
        showItemsLoader()
    }

    private fun isAlreadySelected(selectedTagIndex: Int): Boolean {
        return adapter.selectedFoodTagIndex == selectedTagIndex && tagItemsRecyclerView?.adapter?.itemCount ?: 0 > 0
    }

    //region show and hide views
    private fun showTagsLoader() {
        tagsProgressBar?.visibility = View.VISIBLE
        foodTagsRecyclerView?.visibility = View.GONE
    }

    private fun showFoodTags() {
        tagsProgressBar?.visibility = View.GONE
        foodTagsRecyclerView?.visibility = View.VISIBLE
    }

    private fun showItemsLoader() {
        tagItemsRecyclerView?.visibility = View.GONE
        offlineMessageContainer?.visibility = View.GONE
        menuExtrasContainer?.visibility = View.VISIBLE
        tagItemsProgressBar.visibility = View.VISIBLE
        tagItemsRetryContainer?.visibility = View.GONE
    }

    private fun showItemsLoadingError() {
        offlineMessageContainer?.visibility = View.GONE
        tagItemsProgressBar?.visibility = View.GONE
        tagItemsRetryContainer?.visibility = View.VISIBLE
        menuExtrasContainer?.visibility = View.VISIBLE
        retryButton?.setOnClickListener {
            reloadSelectedTagItems()
        }
    }

    private fun showSelectedTagItems() {
        tagItemsRecyclerView?.visibility = View.VISIBLE
        menuExtrasContainer?.visibility = View.GONE
    }

//endregion
}