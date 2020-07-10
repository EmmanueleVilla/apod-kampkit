package com.shadowings.apodkmp.android.dsl

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.ConstraintLayoutBuilder
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.RecyclerViewBuilder
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.VerticalLayoutBuilder
import com.shadowings.apodkmp.android.dsl.builder.views.ImageBuilder
import com.shadowings.apodkmp.android.dsl.builder.views.TextBuilder

abstract class ALayoutContainerBuilder<T : ViewGroup> {

    abstract fun create(): T

    abstract fun afterBuild()

    private var children: MutableList<View> = mutableListOf()
    protected lateinit var parent: T
    fun build(): T {
        parent = create()
        children.forEach { parent.addView(it) }
        afterBuild()
        return parent
    }

    protected fun imageInternal(
        width: Int,
        height: Int,
        margin: Int,
        bottomMargin: Int?,
        block: ImageBuilder.() -> Unit
    ): AppCompatImageView {
        val view = ImageBuilder()
            .apply(block).build(width, height, margin, bottomMargin)
        children.add(view)
        return view
    }

    protected fun <T : RecyclerView.ViewHolder> horizontalRecyclerInternal(
        adapter: RecyclerView.Adapter<T>,
        width: Int,
        height: Int,
        margin: Int,
        block: RecyclerViewBuilder.() -> Unit
    ): RecyclerView {
        val view = RecyclerViewBuilder()
            .apply(block).build(width, height, margin, adapter)
        children.add(view)
        return view
    }

    protected fun verticalLayoutInternal(width: Int, height: Int, gravity: Int, block: VerticalLayoutBuilder.() -> Unit): LinearLayout {
        var view = VerticalLayoutBuilder(width, height, gravity)
            .apply(block).build()
        children.add(view)
        return view
    }

    protected fun textInternal(
        width: Int,
        height: Int,
        margin: Int,
        bottomMargin: Int?,
        block: TextBuilder.() -> Unit
    ): AppCompatTextView {
        val view = TextBuilder()
            .apply(block).build(width, height, margin, bottomMargin)
        children.add(view)
        return view
    }

    protected fun constraintLayoutInternal(
        width: Int,
        height: Int,
        block: ConstraintLayoutBuilder.() -> Unit
    ): ConstraintLayout {
        val view = ConstraintLayoutBuilder().apply(block).build()
        view.layoutParams = ConstraintLayout.LayoutParams(width, height)
        children.add(view)
        return view
    }
}
