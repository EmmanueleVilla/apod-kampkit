package com.shadowings.apodkmp.android.utils.dsl

import android.content.Context
import android.widget.LinearLayout
import android.widget.ScrollView
import org.koin.core.KoinComponent
import org.koin.core.get

fun verticalScroll(block: ScrollView.() -> LinearLayout): ScrollView {
    val container = object : KoinComponent {
        val ctx: Context = get()
    }
    val view = ScrollView(container.ctx)
    view.addView(block(view))
    return view
}
