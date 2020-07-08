package com.shadowings.apodkmp.android.utils.dsl

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import org.koin.core.KoinComponent
import org.koin.core.get

enum class ConstraintPositions {
    CenterMatch,
    TopLeft,
    BottomLeft
}

class ConstraintLayoutBuilder : ALayoutContainerBuilder<ConstraintLayout>() {

    val set: ConstraintSet = ConstraintSet()
    private val positions: HashMap<View, ConstraintPositions> = HashMap()

    override fun create(): ConstraintLayout {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        return ConstraintLayout(container.ctx)
    }

    override fun afterBuild() {
        positions.forEach {
            when (it.value) {
                ConstraintPositions.CenterMatch -> set.centerInParentWithMargin(it.key, parent, 0)
                ConstraintPositions.TopLeft -> set.connectToTopWithHeightAndMargin(it.key, parent, 100, Dimens.margin)
                ConstraintPositions.BottomLeft -> set.connectToTopWithHeightAndMargin(it.key, parent, 48, Dimens.margin)
            }
        }
        set.applyTo(parent)
        parent.setBackgroundColor(Color.GREEN)
    }

    fun image(
        drawable: Int? = null,
        position: ConstraintPositions,
        block: ImageBuilder.() -> Unit = { }
    ): AppCompatImageView {
        val image = imageInternal(drawable, block)
        positions[image] = position
        return image
    }

    fun text(
        value: String = "",
        size: Float = Dimens.textSizeMedium,
        color: Int = Color.DKGRAY,
        position: ConstraintPositions = ConstraintPositions.CenterMatch,
        block: TextBuilder.() -> Unit = { }
    ): AppCompatTextView {
        val text = textInternal(value, size, color, block)
        positions[text] = position
        return text
    }
}
