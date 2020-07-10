package com.shadowings.apodkmp.android.dsl.builder.views

import android.view.View
import android.view.ViewGroup

abstract class AViewBuilder<V : View> {
    var clickListener: (() -> Unit)? = null

    protected fun addAttributes(view: V, width: Int, height: Int, margin: Int): V {
        view.id = View.generateViewId()
        val lp = ViewGroup.MarginLayoutParams(width, height)
        lp.setMargins(margin, margin, margin, margin)
        view.layoutParams = lp
        if (clickListener != null) {
            view.setOnClickListener { clickListener?.invoke() }
        }
        return addSpecificAttributes(view)
    }

    abstract fun addSpecificAttributes(view: V): V
}
