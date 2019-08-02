package com.elmenus.assignment.menu.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmenus.assignment.R
import com.elmenus.assignment.menu.model.FoodTag
import com.elmenus.assignment.utils.GlideConstants
import kotlinx.android.synthetic.main.tag_item.view.*

class TagViewHolder(
    parent: ViewGroup,
    private var isSelected: Boolean = false
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
) {
    fun bindTagToView(tag: FoodTag?, onClick: (tag: FoodTag?) -> Unit) {
        itemView.tagNameTextView.text = tag?.name
        updateItemImage(itemView, tag)
        setItemClickEvent(tag, itemView, onClick)
    }

    private fun setItemClickEvent(tag: FoodTag?, itemView: View, onClick: (tag: FoodTag?) -> Unit) {
        itemView.setOnClickListener {
            isSelected = true
            itemView.selectedBackground.visibility = View.VISIBLE
            onClick(tag)
        }
    }

    private fun updateItemImage(itemView: View, tag: FoodTag?) {
        Glide.with(itemView.context)
            .load(tag?.photoURL)
            .apply(GlideConstants.defaultRequestOptions)
            .transition(GlideConstants.defaultTransition)
            .into(itemView.tagPhotoImageView)
    }
}