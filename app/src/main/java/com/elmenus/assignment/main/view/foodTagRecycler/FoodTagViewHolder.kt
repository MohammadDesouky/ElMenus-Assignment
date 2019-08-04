package com.elmenus.assignment.main.view.foodTagRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmenus.assignment.R
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.constants.GlideConstants
import kotlinx.android.synthetic.main.food_tag_item.view.*

class FoodTagViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.food_tag_item, parent, false)
) {

    fun markAsSelected() {
        itemView.selectedBackground.visibility = View.VISIBLE
    }

    fun removeSelection() {
        itemView.selectedBackground.visibility = View.GONE
    }

    fun bindTagToView(tagIndex: Int, tag: FoodTag?, onClick: (tagIndex: Int, tag: FoodTag?) -> Unit) {
        itemView.tagNameTextView.text = tag?.name
        updateItemImage(itemView, tag)
        setItemClickEvent(tagIndex, tag, onClick)
    }

    private fun setItemClickEvent(
        tagIndex: Int,
        tag: FoodTag?,
        onClick: (tagIndex: Int, tag: FoodTag?) -> Unit
    ) {
        itemView.setOnClickListener {
            onClick(tagIndex, tag)
        }
    }

    private fun updateItemImage(itemView: View, tag: FoodTag?) {
        Glide.with(itemView.context)
            .load(tag?.tagPhotoUrl)
            .apply(GlideConstants.defaultRequestOptions)
            .transition(GlideConstants.defaultTransition)
            .into(itemView.tagPhotoImageView)
    }
}