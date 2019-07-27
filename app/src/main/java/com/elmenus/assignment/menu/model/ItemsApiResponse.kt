package com.elmenus.assignment.menu.model

import com.google.gson.annotations.SerializedName


data class ItemsApiResponse(@SerializedName("items") val items: ArrayList<Item>)

data class Item(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("photoUrl") val photoURL: String,
    @SerializedName("description") val description: String
)