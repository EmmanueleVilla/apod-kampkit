package com.shadowings.apodkmp.android.dsl.builder.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import org.koin.core.KoinComponent
import org.koin.core.get

class TextBuilder {
    fun build(text: String, size: Float, color: Int): AppCompatTextView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = AppCompatTextView(container.ctx)
        view.setTextColor(color)
        view.id = View.generateViewId()
        view.text = text
        view.elevation = 10.0F
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(Dimens.margin, Dimens.margin, Dimens.margin, Dimens.margin)
        view.layoutParams = lp
        view.textSize = size
        return view
    }
}
