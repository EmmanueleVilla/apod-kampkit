package com.shadowings.apodkmp.android.dsl.builder.views

import android.graphics.Color
import android.view.View
import android.view.ViewGroup

abstract class AViewBuilder<V : View> {
    var clickListener: (() -> Unit)? = null
    var backgroundColor: Int = Color.TRANSPARENT

    protected fun addAttributes(view: V, width: Int, height: Int, margin: Int, bottomMargin: Int?): V {
        view.id = View.generateViewId()
        val lp = ViewGroup.MarginLayoutParams(width, height)
        lp.setMargins(margin, margin, margin, bottomMargin ?: margin)
        view.layoutParams = lp
        view.setBackgroundColor(backgroundColor)
        if (clickListener != null) {
            view.setOnClickListener { clickListener?.invoke() }
        }
        return addSpecificAttributes(view)
    }

    abstract fun addSpecificAttributes(view: V): V
}
