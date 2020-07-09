package com.shadowings.apodkmp.android.utils.dsl

import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

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
        drawable: Int?,
        width: Int,
        height: Int,
        block: ImageBuilder.() -> Unit
    ): AppCompatImageView {
        val view = ImageBuilder().apply(block).build(drawable, width, height)
        children.add(view)
        return view
    }

    protected fun <T : RecyclerView.ViewHolder> horizontalRecyclerInternal(
        adapter: RecyclerView.Adapter<T>,
        height: Int,
        block: RecyclerViewBuilder.() -> Unit
    ): RecyclerView {
        val view = RecyclerViewBuilder().apply(block).build(height, adapter)
        children.add(view)
        return view
    }

    protected fun textInternal(
        text: String,
        size: Float,
        color: Int,
        block: TextBuilder.() -> Unit
    ): AppCompatTextView {
        val view = TextBuilder().apply(block).build(text, size, color)
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
