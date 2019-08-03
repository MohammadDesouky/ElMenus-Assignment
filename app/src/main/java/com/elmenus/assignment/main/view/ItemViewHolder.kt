package com.elmenus.assignment.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmenus.assignment.R
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.constants.GlideConstants
import kotlinx.android.synthetic.main.food_item_card.view.*

class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.food_item_card, parent, false)
) {
    fun bindItemToView(item: Item?, onClick: (item: Item?, imageView: ImageView) -> Unit) {
        itemView.itemNameTextView.text = item?.getItemName
        itemView.itemDescriptionTextView.text = item?.description
        Glide.with(itemView.context)
            .load(item?.photoURL)
            .transition(GlideConstants.defaultTransition)
            .apply(GlideConstants.defaultRequestOptions)
            .into(itemView.itemPhotoImageView)
        itemView.setOnClickListener {
            onClick(item, itemView.itemPhotoImageView)
        }
    }
}
