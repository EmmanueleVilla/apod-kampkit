package com.shadowings.apodkmp.android.utils.dsl

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import org.koin.core.KoinComponent
import org.koin.core.get

class ImageBuilder {
    fun build(): AppCompatImageView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val image = AppCompatImageView(container.ctx)
        image.id = View.generateViewId()
        return image
    }
}
