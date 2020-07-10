package com.shadowings.apodkmp.android.dsl.builder.viewgroups

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.shadowings.apodkmp.android.dsl.builder.views.AViewBuilder
import org.koin.core.KoinComponent
import org.koin.core.get

class RecyclerViewBuilder : AViewBuilder<RecyclerView>() {
    fun <T : RecyclerView.ViewHolder> build(width: Int, height: Int, margin: Int, adapter: RecyclerView.Adapter<T>): RecyclerView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = RecyclerView(container.ctx)
        view.id = View.generateViewId()
        val layoutManager = LinearLayoutManager(container.ctx, HORIZONTAL, false)
        view.layoutManager = layoutManager
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        view.adapter = adapter
        return addAttributes(view, width, height, margin, margin)
    }

    override fun addSpecificAttributes(view: RecyclerView): RecyclerView {
        return view
    }
}
