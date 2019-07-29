package com.elmenus.assignment.menu.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmenus.assignment.R
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.utils.GlideConstants
import kotlinx.android.synthetic.main.menu_item.view.*

class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
) {
    fun bindItemToView(item: Item?, onClick: (item: Item?, imageView: ImageView) -> Unit) {
        itemView.itemNameTextView.text = item?.name
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
