package com.elmenus.assignment.main.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elmenus.assignment.constants.AppConstants
import com.google.gson.annotations.SerializedName


data class FoodTagItemsApiResponse(@SerializedName("items") val items: ArrayList<Item>)

@Entity(tableName = "TagItems")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int,
    @SerializedName("id") val apiId: String,
    @SerializedName("name") val name: String,
    @SerializedName("photoUrl") val photoURL: String,
    @SerializedName("description") val description: String
) {
    var tagName: String = ""

    val getItemName :String
        get() {
            try {
                if (AppConstants.SHOW_BEAUTIFIED_NAMES) {
                    return name.split("-").last()
                }
            } catch (ignored: Exception) {
            }
            return name
        }
}