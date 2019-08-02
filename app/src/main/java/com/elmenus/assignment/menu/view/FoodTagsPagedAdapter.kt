package com.elmenus.assignment.menu.view

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.elmenus.assignment.menu.model.FoodTag

class FoodTagsPagedAdapter(private var onItemClicked: (tag: FoodTag?) -> Unit) :
    PagedListAdapter<FoodTag, TagViewHolder>(tagsComparator) {

    companion object {

        private val tagsComparator = object : DiffUtil.ItemCallback<FoodTag>() {
            override fun areItemsTheSame(oldTag: FoodTag, newTag: FoodTag): Boolean {
                return oldTag.name == newTag.name
            }

            override fun areContentsTheSame(oldTag: FoodTag, newTag: FoodTag): Boolean {
                return oldTag.name == newTag.name && oldTag.photoURL == newTag.photoURL
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagViewHolder(parent)

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) =
        holder.bindTagToView(getItem(position), onItemClicked)
}