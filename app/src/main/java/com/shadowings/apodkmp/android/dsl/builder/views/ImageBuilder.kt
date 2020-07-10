package com.shadowings.apodkmp.android.dsl.builder.views

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import org.koin.core.KoinComponent
import org.koin.core.get

class ImageBuilder : AViewBuilder<AppCompatImageView>() {
    var res: Int? = null
    var scale: ImageView.ScaleType = ImageView.ScaleType.FIT_XY

    fun build(width: Int, height: Int, margin: Int, bottomMargin: Int?): AppCompatImageView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val image = AppCompatImageView(container.ctx)
        return addAttributes(image, width, height, margin, bottomMargin)
    }

    override fun addSpecificAttributes(view: AppCompatImageView): AppCompatImageView {
        return view.apply {
            scaleType = scale
            if (res != null) {
                setImageResource(res!!)
            }
        }
    }
}
