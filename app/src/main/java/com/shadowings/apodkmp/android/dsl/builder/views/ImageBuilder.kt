package com.shadowings.apodkmp.android.dsl.builder.views

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import org.koin.core.KoinComponent
import org.koin.core.get

class ImageBuilder : AViewBuilder<AppCompatImageView>() {
    var res: Int? = null
    var backgroundColor: Int = android.R.color.transparent
    var scale: ImageView.ScaleType = ImageView.ScaleType.FIT_XY

    fun build(width: Int, height: Int, margin: Int): AppCompatImageView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val image = AppCompatImageView(container.ctx)
        return addAttributes(image, width, height, margin)
    }

    override fun addSpecificAttributes(view: AppCompatImageView): AppCompatImageView {
        return view.apply {
            setBackgroundColor(backgroundColor)
            scaleType = scale
            if (res != null) {
                setImageResource(res!!)
            }
        }
    }
}
