package com.elmenus.assignment.menu.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.assignment.menu.model.Item

@Dao
interface TagsItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Item>)

    @Query("SELECT * FROM TagItems WHERE tagName=:tagName")
    fun getAllItemsOf(tagName: String): List<Item>
}
