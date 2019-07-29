package com.elmenus.assignment.menu.view

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.elmenus.assignment.menu.model.Item

class ItemsRecyclerViewAdapter(
    private val items: ArrayList<Item>,
    private val onItemClicked: (item: Item?, imageView: ImageView) -> Unit
) :
    RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bindItemToView(getItem(position), onItemClicked)

    private fun getItem(position: Int): Item? {
        try {
            return items[position]
        } catch (ignored: Exception) {
            return null
        }
    }
}