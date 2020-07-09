package com.shadowings.apodkmp.android.utils.dsl

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinComponent
import org.koin.core.get

fun verticalLayout(block: VerticalLayoutBuilder.() -> Unit): LinearLayout = VerticalLayoutBuilder().apply(block).build()

class VerticalLayoutBuilder : ALayoutContainerBuilder<LinearLayout>() {
    override fun create(): LinearLayout {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = LinearLayout(container.ctx)
        view.orientation = LinearLayout.VERTICAL
        return view
    }

    fun constraintLayout(
        width: Int = MATCH_PARENT,
        height: Int = MATCH_PARENT,
        block: ConstraintLayoutBuilder.() -> Unit = { }
    ): ConstraintLayout = constraintLayoutInternal(width, height, block)

    fun image(
        drawable: Int? = null,
        width: Int = MATCH_PARENT,
        height: Int = MATCH_PARENT,
        block: ImageBuilder.() -> Unit = { }
    ): AppCompatImageView = imageInternal(drawable, width, height, block)

    fun text(
        value: String = "",
        size: Float = Dimens.textSizeMedium,
        color: Int = Color.DKGRAY,
        block: TextBuilder.() -> Unit = { }
    ): AppCompatTextView = textInternal(value, size, color, block)

    fun <T : RecyclerView.ViewHolder> horizontalRecycler(
        adapter: RecyclerView.Adapter<T>,
        height: Int = MATCH_PARENT,
        block: RecyclerViewBuilder.() -> Unit = { }
    ): RecyclerView = horizontalRecyclerInternal(adapter, height, block)

    override fun afterBuild() {
    }
}
