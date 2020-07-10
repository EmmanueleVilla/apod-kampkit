package com.shadowings.apodkmp.android.dsl.builder.views

import android.content.Context
import android.graphics.Color
import androidx.appcompat.widget.AppCompatTextView
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import org.koin.core.KoinComponent
import org.koin.core.get

class TextBuilder : AViewBuilder<AppCompatTextView>() {
    var size: Float = Dimens.textSizeMedium
    var color: Int = Color.DKGRAY
    var label: String = ""

    fun build(width: Int, height: Int, margin: Int): AppCompatTextView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = AppCompatTextView(container.ctx)

        return addAttributes(view, width, height, margin)
    }

    override fun addSpecificAttributes(view: AppCompatTextView): AppCompatTextView {
        return view.apply {
            textSize = size
            setTextColor(color)
            text = label
        }
    }
}
