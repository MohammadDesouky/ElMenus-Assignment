package com.elmenus.assignment.menu.repository.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.assignment.menu.model.FoodTag

@Dao
interface  FoodTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags : List<FoodTag>)

    @Query("SELECT * FROM FoodTags")
    fun getAllTags() : DataSource.Factory<Int, FoodTag>


}
