package com.shadowings.apodkmp.android.dsl.builder.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import org.koin.core.KoinComponent
import org.koin.core.get

class ImageBuilder {
    fun build(
        drawable: Int?,
        scaleType: ImageView.ScaleType,
        width: Int,
        height: Int,
        backgroundColor: Int
    ): AppCompatImageView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val image = AppCompatImageView(container.ctx)
        image.id = View.generateViewId()
        if (drawable != null) {
            image.setBackgroundResource(drawable)
        }
        image.setBackgroundColor(backgroundColor)
        image.scaleType = scaleType
        image.layoutParams = ViewGroup.LayoutParams(width, height)
        return image
    }
}
