package com.elmenus.assignment.main.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.elmenus.assignment.constants.AppConstants
import com.google.gson.annotations.SerializedName

data class FoodTagsApiResponse(@SerializedName("tags") val foodTags: ArrayList<FoodTag>)

@Entity(tableName = "FoodTags")
data class FoodTag(
    @PrimaryKey
    @SerializedName("tagName") val tagName: String,
    @SerializedName("photoURL") val tagPhotoUrl: String
) {
    @get:Ignore
    val name: String
        get() {
            try {
                if (AppConstants.SHOW_BEAUTIFIED_NAMES) {
                    return tagName.split("-").last()
                }
            } catch (ignored: Exception) {
            }
            return tagName
        }
}