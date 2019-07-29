package com.elmenus.assignment.utils

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object GlideConstants {

    val defaultRequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
    val defaultTransition = DrawableTransitionOptions().crossFade()
}