package com.elmenus.assignment.menu.model

import com.google.gson.annotations.SerializedName

data class TagsApiResponse(@SerializedName("tags") val menuTags: ArrayList<Tag>)

data class Tag(
    @SerializedName("tagName") private val tag: String,
    @SerializedName("photoURL") val photoURL: String
) {
    val name: String
        get() {
            try {
                return tag.split("-")[1]
            } catch (ignored: Exception) {
            }
            return tag
        }
}