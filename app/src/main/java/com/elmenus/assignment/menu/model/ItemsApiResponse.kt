package com.elmenus.assignment.menu.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ItemsApiResponse(@SerializedName("items") val items: ArrayList<Item>)

@Entity(tableName = "Items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("id") val apiId: String,
    @SerializedName("name") val name: String,
    @SerializedName("photoUrl") val photoURL: String,
    @SerializedName("description") val description: String
) {
    var tagName: String = ""
}