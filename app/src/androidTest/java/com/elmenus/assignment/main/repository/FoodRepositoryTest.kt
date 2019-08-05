package com.elmenus.assignment.main.repository

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.elmenus.assignment.main.repository.db.FoodDatabase
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FoodRepositoryTest {

    private lateinit var db: FoodDatabase
    private lateinit var repository: FoodRepository
    private val LATCH_TIMEOUT_IN_SECONDS = 15L

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        db = FoodDatabase.create(context)
        db.clearAllTables()
        repository = FoodRepository(db, context)
    }

    @Test
    fun testLoadingTagsFromApiAndSaveThemInDatabase() {
        var hasTags = false
        val latch = CountDownLatch(1)
        repository.observableTags.observeForever { tags ->
            if (tags?.isNotEmpty() == true) {
                hasTags = true
                repository.observableTags.removeObserver { this }
                latch.countDown()
            }
        }
        repository.reloadTags()
        latch.await(LATCH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        assertTrue(hasTags)
    }

    @Test
    fun testLoadingTagItemsFromApiAndSaveThemInDatabase() {
        val latch = CountDownLatch(2)
        var selectedTagName = ""
        repository.observableTags.observeForever { tags ->
            if (tags.isNotEmpty() && selectedTagName.isEmpty()) {
                selectedTagName = tags[0]?.tagName ?: ""
                repository.observableTags.removeObserver { this }
                latch.countDown()
            }
        }
        repository.reloadTags()
        latch.await(LATCH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        var allItemsNamesStartWithTagName = true
        var dataLoaded = false
        if (selectedTagName.isNotEmpty()) {
            repository.observableItemsOfSelectedTag.observeForever { items ->
                if (items.isNotEmpty()) {
                    dataLoaded = true
                    items.forEach { item ->
                        if (!item.name.startsWith(selectedTagName)) {
                            allItemsNamesStartWithTagName = false
                            return@forEach
                        }
                    }
                } else {
                    throw Exception("Empty Items List")
                }
                repository.observableItemsOfSelectedTag.removeObserver { this }
                latch.countDown()
            }
            repository.setSelectedTagByName(selectedTagName)
        } else {
            throw Exception("Empty Selected Tag Name")
        }
        latch.await(LATCH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        assertTrue(allItemsNamesStartWithTagName && dataLoaded)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }
}