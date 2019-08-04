package com.elmenus.assignment.main.view.foodItemsRecycler

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.elmenus.assignment.main.model.Item

class FoodItemsRecyclerViewAdapter(
    private val items: ArrayList<Item>,
    private val onItemClicked: (item: Item?, imageView: ImageView) -> Unit
) :
    RecyclerView.Adapter<FoodItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoodItemViewHolder(parent)

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) =
        holder.bindItemToView(getItem(position), onItemClicked)

    private fun getItem(position: Int): Item? {
        try {
            return items[position]
        } catch (ignored: Exception) {
            return null
        }
    }
}