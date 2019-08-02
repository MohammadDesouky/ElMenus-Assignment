package com.elmenus.assignment.menu.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.FoodTag
import com.elmenus.assignment.menu.repository.db.dao.TagsItemsDao
import com.elmenus.assignment.menu.repository.db.dao.FoodTagsDao

@Database(
    entities = [Item::class, FoodTag::class],
    version = 1,
    exportSchema = false
)
abstract class MenuDB : RoomDatabase() {

    companion object {

        private const val DB_NAME = "elmenusAssignmentDatabase.db"

        fun create(context: Context): MenuDB {
            return Room.databaseBuilder(context, MenuDB::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun tagsDao(): FoodTagsDao

    abstract fun itemsDao(): TagsItemsDao
}