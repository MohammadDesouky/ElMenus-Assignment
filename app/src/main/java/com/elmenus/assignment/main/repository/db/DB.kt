package com.elmenus.assignment.main.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elmenus.assignment.main.model.Item
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.repository.db.dao.TagsItemsDao
import com.elmenus.assignment.main.repository.db.dao.FoodTagsDao

@Database(
    entities = [Item::class, FoodTag::class],
    version = 1,
    exportSchema = false
)
abstract class DB : RoomDatabase() {

    companion object {

        private const val DB_NAME = "database.db"

        fun create(context: Context): DB {
            return Room.databaseBuilder(context, DB::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun tagsDao(): FoodTagsDao

    abstract fun itemsDao(): TagsItemsDao
}