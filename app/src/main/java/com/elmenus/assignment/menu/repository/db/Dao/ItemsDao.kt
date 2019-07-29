package com.elmenus.assignment.menu.repository.db.Dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.assignment.menu.model.Item

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Item>)

    @Query("SELECT * FROM Items WHERE tagName=:tagName")
    fun getAllItemsOf(tagName: String): ArrayList<Item>
}
