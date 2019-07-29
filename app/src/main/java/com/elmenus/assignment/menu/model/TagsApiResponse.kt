package com.elmenus.assignment.menu.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TagsApiResponse(@SerializedName("tags") val menuTags: ArrayList<Tag>)

@Entity(tableName = "Tags")
data class Tag(
    @PrimaryKey
    @SerializedName("tagName") private val tag: String,
    @SerializedName("photoURL") val photoURL: String
) {
    @get:Ignore
    val name: String
        get() {
            try {
                return tag.split("-")[1]
            } catch (ignored: Exception) {
            }
            return tag
        }
}