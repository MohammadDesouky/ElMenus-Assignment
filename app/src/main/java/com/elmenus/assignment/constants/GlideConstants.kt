package com.elmenus.assignment.constants

import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.elmenus.assignment.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

object GlideConstants {

    private const val IMAGE_BORDER_RADIUS = 8

    val defaultRequestOptions = bitmapTransform(
        MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(IMAGE_BORDER_RADIUS, 0, RoundedCornersTransformation.CornerType.ALL)
        )
    ).diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.mipmap.placeholder)
        .error(R.mipmap.error)

    val defaultTransition = DrawableTransitionOptions().crossFade()
}
