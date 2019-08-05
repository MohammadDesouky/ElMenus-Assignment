package com.elmenus.assignment.main.repository.db.dao

import androidx.paging.DataSource
import androidx.room.*
import com.elmenus.assignment.main.model.FoodTag

@Dao
interface  FoodTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags : List<FoodTag>)

    @Query("SELECT * FROM FoodTags")
    fun getAllTags() : DataSource.Factory<Int, FoodTag>
}
