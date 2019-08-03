package com.elmenus.assignment.main.view

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.elmenus.assignment.main.model.FoodTag

class FoodTagsPagedAdapter(private var onItemClicked: (tagIndex: Int, tag: FoodTag?) -> Unit) :
    PagedListAdapter<FoodTag, FoodTagViewHolder>(tagsComparator) {

    var selectedFoodTagIndex = 1

    companion object {

        private val tagsComparator = object : DiffUtil.ItemCallback<FoodTag>() {
            override fun areItemsTheSame(oldTag: FoodTag, newTag: FoodTag): Boolean {
                return oldTag.name == newTag.name
            }

            override fun areContentsTheSame(oldTag: FoodTag, newTag: FoodTag): Boolean {
                return oldTag.name == newTag.name && oldTag.tagPhotoUrl == newTag.tagPhotoUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FoodTagViewHolder(parent)

    override fun onBindViewHolder(holder: FoodTagViewHolder, position: Int) {
        if (position == selectedFoodTagIndex) {
            holder.markAsSelected()
        } else {
            holder.removeSelection()
        }
        holder.bindTagToView(position, getItem(position), onItemClicked)
    }

}