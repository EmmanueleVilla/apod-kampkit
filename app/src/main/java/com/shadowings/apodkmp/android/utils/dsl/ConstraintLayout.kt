package com.shadowings.apodkmp.android.utils.dsl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
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

    private val positions: HashMap<View, ConstraintPositions> = HashMap()

    override fun create(): ConstraintLayout {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        return ConstraintLayout(container.ctx)
    }

    override fun afterBuild() {
        val set = ConstraintSet()
        for (it in positions) {
            when (it.value) {
                ConstraintPositions.CenterMatch -> set.centerInParent(it.key, parent, 0)
                ConstraintPositions.TopLeft -> set.connectTo(Connection.Top, it.key, parent)
                ConstraintPositions.BottomLeft -> set.connectTo(Connection.Bottom, it.key, parent)
            }
        }
        parent.setConstraintSet(set)
        set.applyTo(parent)
    }

    fun image(
        drawable: Int? = null,
        position: ConstraintPositions,
        width: Int = MATCH_PARENT,
        height: Int = MATCH_PARENT,
        block: ImageBuilder.() -> Unit = { }
    ): AppCompatImageView {
        val image = imageInternal(drawable, block)
        image.layoutParams = ViewGroup.LayoutParams(width, height)
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
