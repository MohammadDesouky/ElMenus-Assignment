package com.elmenus.assignment.menu.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elmenus.assignment.menu.model.Item
import com.elmenus.assignment.menu.model.Tag
import com.elmenus.assignment.menu.repository.db.Dao.ItemsDao
import com.elmenus.assignment.menu.repository.db.Dao.TagsDao

@Database(
    entities = [Item::class, Tag::class],
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

    abstract fun tagsDao(): TagsDao

    abstract fun itemsDao(): ItemsDao
}