package com.elmenus.assignment.itemDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.elmenus.assignment.R
import com.elmenus.assignment.constants.GlideConstants
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity() {

    companion object {
        const val ITEM_PHOTO_KEY = "ITEM_PHOTO_KEY"
        const val ITEM_NAME_KEY = "ITEM_NAME_KEY"
        const val ITEM_DESCRIPTION_KEY = "ITEM_DESCRIPTION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        fillItemDetails()
    }

    private fun fillItemDetails() {
        val photoURL = intent?.extras?.getString(ITEM_PHOTO_KEY)
        val name = intent?.extras?.getString(ITEM_NAME_KEY)
        val description = intent?.extras?.getString(ITEM_DESCRIPTION_KEY)
        Glide.with(this)
            .load(photoURL)
            .transition(GlideConstants.defaultTransition)
            .apply(GlideConstants.defaultRequestOptions)
            .into(itemPhotoImageView)

        collapsingToolBar?.isTitleEnabled = true
        collapsingToolBar?.title = name
        itemDescriptionTextView.text=description
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}
