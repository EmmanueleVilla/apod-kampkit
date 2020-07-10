package com.shadowings.apodkmp.android.dsl.builder.viewgroups

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shadowings.apodkmp.android.dsl.ALayoutContainerBuilder
import com.shadowings.apodkmp.android.dsl.builder.views.ImageBuilder
import com.shadowings.apodkmp.android.dsl.builder.views.TextBuilder
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import org.koin.core.KoinComponent
import org.koin.core.get

fun verticalLayout(gravity: Int = Gravity.NO_GRAVITY, block: VerticalLayoutBuilder.() -> Unit): LinearLayout = VerticalLayoutBuilder(
    MATCH_PARENT, MATCH_PARENT, gravity)
    .apply(block).build()

class VerticalLayoutBuilder(private val width: Int, private val height: Int, private val gravity: Int) : ALayoutContainerBuilder<LinearLayout>() {
    override fun create(): LinearLayout {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = LinearLayout(container.ctx)
        view.layoutParams = ViewGroup.LayoutParams(width, height)
        view.orientation = LinearLayout.VERTICAL
        return view
    }

    fun constraintLayout(
        width: Int = MATCH_PARENT,
        height: Int = MATCH_PARENT,
        block: ConstraintLayoutBuilder.() -> Unit = { }
    ): ConstraintLayout = constraintLayoutInternal(width, height, block)

    fun image(
        width: Int = MATCH_PARENT,
        height: Int = MATCH_PARENT,
        margin: Int = Dimens.margin,
        bottomMargin: Int? = null,
        block: ImageBuilder.() -> Unit = { }
    ): AppCompatImageView = imageInternal(width, height, margin, bottomMargin, block)

    fun text(
        width: Int = MATCH_PARENT,
        height: Int = WRAP_CONTENT,
        margin: Int = Dimens.margin,
        bottomMargin: Int? = null,
        block: TextBuilder.() -> Unit = { }
    ): AppCompatTextView = textInternal(width, height, margin, bottomMargin, block)

    fun verticalLayout(width: Int = MATCH_PARENT, height: Int = WRAP_CONTENT, gravity: Int = Gravity.NO_GRAVITY, block: VerticalLayoutBuilder.() -> Unit): LinearLayout =
        verticalLayoutInternal(width, height, gravity, block)

    fun <T : RecyclerView.ViewHolder> horizontalRecycler(
        adapter: RecyclerView.Adapter<T>,
        height: Int = MATCH_PARENT,
        block: RecyclerViewBuilder.() -> Unit = { }
    ): RecyclerView = horizontalRecyclerInternal(adapter, MATCH_PARENT, height, 0, block)

    override fun afterBuild() {
        parent.gravity = gravity
    }
}
