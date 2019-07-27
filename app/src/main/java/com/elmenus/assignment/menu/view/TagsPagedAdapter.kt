package com.elmenus.assignment.menu.view

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.elmenus.assignment.menu.model.Tag

class TagsPagedAdapter(private var onItemClicked: (tag: Tag?) -> Unit) :
    PagedListAdapter<Tag, TagViewHolder>(tagsComparator) {

    companion object {

        private val tagsComparator = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldTag: Tag, newTag: Tag): Boolean {
                return oldTag.name == newTag.name
            }

            override fun areContentsTheSame(oldTag: Tag, newTag: Tag): Boolean {
                return oldTag.name == newTag.name && oldTag.photoURL == newTag.photoURL
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagViewHolder(parent)

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) =
        holder.bindTagToView(getItem(position), onItemClicked)
}