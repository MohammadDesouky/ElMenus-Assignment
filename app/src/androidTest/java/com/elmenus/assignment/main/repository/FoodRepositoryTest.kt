package com.elmenus.assignment.main.repository

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.elmenus.assignment.main.model.FoodTag
import com.elmenus.assignment.main.repository.db.FoodDatabase
import com.elmenus.assignment.main.view.MainActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
class FoodRepositoryTest {

    private lateinit var repository: FoodRepository
    private lateinit var db: FoodDatabase
    private lateinit var mockedActivity: MainActivity
    private val LATCH_TIMEOUT_IN_SECONDS = 30L


    @Before
    fun setup() {
        mockedActivity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

        db = FoodDatabase.create(mockedActivity)
        repository = FoodRepository(db, mockedActivity)
    }


    @Test
    fun testLoadingTagItemsFromApiAndDatabase() {
        val latch = CountDownLatch(1)
        var loadedTags: PagedList<FoodTag>? = null
        repository.observableTags.observe(mockedActivity, Observer
        { x ->
            loadedTags = x
            latch.countDown()

        })
        repository.invalidateData()
        latch.await(LATCH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        assertTrue(loadedTags?.isNotEmpty() == true)
    }

    @After
    fun tearDown() {

    }
}