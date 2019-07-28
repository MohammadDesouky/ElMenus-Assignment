package com.elmenus.assignment.menu.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmenus.assignment.R
import com.elmenus.assignment.menu.model.Tag
import kotlinx.android.synthetic.main.tag_item.view.*

class TagViewHolder(parent: ViewGroup, private var isSelected: Boolean = false) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
) {
    fun bindTagToView(tag: Tag?, onClick: (tag: Tag?) -> Unit) {
        itemView.tagNameTextView.text = tag?.name
        Glide.with(itemView.context)
            .load(tag?.photoURL)
            .into(itemView.tagPhotoImageView)
        itemView.setOnClickListener {
            isSelected = true
            itemView.selectedBackground.visibility = View.VISIBLE
            onClick(tag)
        }
    }
}