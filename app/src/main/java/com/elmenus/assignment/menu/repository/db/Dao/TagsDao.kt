package com.elmenus.assignment.menu.repository.db.Dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.assignment.menu.model.Tag

@Dao
interface  TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags : List<Tag>)

    @Query("SELECT * FROM tags")
    fun getAllTags() : DataSource.Factory<Int, Tag>


}
