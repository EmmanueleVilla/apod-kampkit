package com.shadowings.apodkmp.android.dsl.builder.viewgroups

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
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

fun verticalLayout(block: VerticalLayoutBuilder.() -> Unit): LinearLayout = VerticalLayoutBuilder()
    .apply(block).build()

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
        scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_XY,
        backgroundColor: Int = Color.TRANSPARENT,
        block: ImageBuilder.() -> Unit = { }
    ): AppCompatImageView = imageInternal(drawable, width, height, scaleType, backgroundColor, block)

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
