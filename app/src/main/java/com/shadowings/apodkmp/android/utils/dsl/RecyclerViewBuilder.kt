package com.shadowings.apodkmp.android.utils.dsl

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinComponent
import org.koin.core.get

class RecyclerViewBuilder {
    fun <T : RecyclerView.ViewHolder> build(height: Int, adapter: RecyclerView.Adapter<T>): RecyclerView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = RecyclerView(container.ctx)
        view.id = View.generateViewId()
        val layoutManager = LinearLayoutManager(container.ctx, HORIZONTAL, false)
        view.layoutManager = layoutManager
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        view.adapter = adapter
        return view
    }
}
