package com.shadowings.apodkmp.android.dsl.builder.viewgroups

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
import androidx.constraintlayout.widget.ConstraintSet
import com.shadowings.apodkmp.android.dsl.ALayoutContainerBuilder
import com.shadowings.apodkmp.android.dsl.builder.views.ImageBuilder
import com.shadowings.apodkmp.android.dsl.builder.views.TextBuilder
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import com.shadowings.apodkmp.android.dsl.utils.centerInParent
import org.koin.core.KoinComponent
import org.koin.core.get

enum class ConstraintPositions {
    Center,
    TopStart,
    BottomStart,
    BottomEnd
}

private enum class Constraints {
    Center,
    Top,
    Bottom,
    Start,
    End,
    Height,
    Width
}

fun constraintLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    block: ConstraintLayoutBuilder.() -> Unit = { }
): ConstraintLayout {
    val view = ConstraintLayoutBuilder().apply(block).build()
    view.layoutParams = ConstraintLayout.LayoutParams(width, height)
    return view
}

class ConstraintLayoutBuilder : ALayoutContainerBuilder<ConstraintLayout>() {

    private val constraints: MutableList<Triple<Constraints, View, Int>> = mutableListOf()

    override fun create(): ConstraintLayout {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val layout = ConstraintLayout(container.ctx)
        layout.id = View.generateViewId()
        return layout
    }

    override fun afterBuild() {

        val set = ConstraintSet()
        for (it in constraints) {
            when (it.first) {
                Constraints.Center -> set.centerInParent(it.second, parent, it.third)
                Constraints.Top -> set.connect(it.second.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP, it.third)
                Constraints.Bottom -> set.connect(it.second.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM, it.third)
                Constraints.Start -> set.connect(it.second.id, ConstraintSet.START, parent.id, ConstraintSet.START, it.third)
                Constraints.End -> set.connect(it.second.id, ConstraintSet.END, parent.id, ConstraintSet.END, it.third)
                Constraints.Height -> set.constrainHeight(it.second.id, it.third)
                Constraints.Width -> set.constrainWidth(it.second.id, it.third)
            }
        }
        parent.setConstraintSet(set)
        set.applyTo(parent)
    }

    private fun addConstraints(
        view: View,
        position: ConstraintPositions,
        width: Int,
        height: Int,
        margin: Int
    ) {
        when (position) {
            ConstraintPositions.Center -> {
                constraints.add(Triple(Constraints.Center, view, margin))
            }
            ConstraintPositions.TopStart -> {
                constraints.add(Triple(Constraints.Top, view, margin))
                constraints.add(Triple(Constraints.Start, view, margin))
            }
            ConstraintPositions.BottomStart -> {
                constraints.add(Triple(Constraints.Bottom, view, margin))
                constraints.add(Triple(Constraints.Start, view, margin))
            }
            ConstraintPositions.BottomEnd -> {
                constraints.add(Triple(Constraints.Bottom, view, margin))
                constraints.add(Triple(Constraints.End, view, margin))
            }
        }
        if (width > 0) {
            constraints.add(Triple(Constraints.Width, view, width))
        }
        if (height > 0) {
            constraints.add(Triple(Constraints.Height, view, height))
        }
    }

    fun image(
        position: ConstraintPositions,
        margin: Int = Dimens.margin,
        width: Int = MATCH_CONSTRAINT,
        height: Int = MATCH_CONSTRAINT,
        block: ImageBuilder.() -> Unit = { }
    ): AppCompatImageView {
        val image = imageInternal(width, height, block)
        addConstraints(image, position, width, height, margin)
        return image
    }

    fun text(
        margin: Int = Dimens.margin,
        position: ConstraintPositions,
        width: Int = MATCH_CONSTRAINT,
        height: Int = MATCH_CONSTRAINT,
        block: TextBuilder.() -> Unit = { }
    ): AppCompatTextView {
        val text = textInternal(block)
        text.layoutParams = ViewGroup.LayoutParams(width, height)
        addConstraints(text, position, width, height, margin)
        return text
    }
}
