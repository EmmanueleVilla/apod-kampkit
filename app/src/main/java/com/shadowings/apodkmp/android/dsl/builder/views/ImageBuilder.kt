package com.shadowings.apodkmp.android.dsl.builder.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import org.koin.core.KoinComponent
import org.koin.core.get

class ImageBuilder {
    private lateinit var image: AppCompatImageView

    var clickListener: (() -> Unit)? = null
    var resource: Int? = null
    var backgroundColor: Int = android.R.color.transparent
    var scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_XY

    fun build(width: Int, height: Int): AppCompatImageView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        image = AppCompatImageView(container.ctx)
        image.id = View.generateViewId()

        image.setBackgroundColor(backgroundColor)
        image.scaleType = scaleType
        image.layoutParams = ViewGroup.LayoutParams(width, height)

        if (this.resource != null) {
            image.setImageResource(resource!!)
        }

        if (clickListener != null) {
            image.setOnClickListener { clickListener?.invoke() }
        }
        return image
    }
}
