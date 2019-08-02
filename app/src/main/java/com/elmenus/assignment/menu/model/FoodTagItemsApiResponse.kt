package com.elmenus.assignment.menu.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FoodTagItemsApiResponse(@SerializedName("items") val items: ArrayList<Item>)

@Entity(tableName = "TagItems")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Expose @SerializedName("id") val apiId: String,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("photoUrl") val photoURL: String,
    @Expose @SerializedName("description") val description: String
) {
    var tagName: String = ""
}