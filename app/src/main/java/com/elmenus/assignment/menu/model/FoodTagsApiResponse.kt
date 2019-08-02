package com.elmenus.assignment.menu.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FoodTagsApiResponse(@SerializedName("tags") val foodTags: ArrayList<FoodTag>)

@Entity(tableName = "FoodTags")
data class FoodTag(
    @PrimaryKey
    @Expose @SerializedName("tagName") val tagName: String,
    @Expose @SerializedName("photoURL") val photoURL: String
) {
    @get:Ignore
    val name: String
        get() {
            try {
                return tagName.split("-")[1]
            } catch (ignored: Exception) {
            }
            return tagName
        }
}