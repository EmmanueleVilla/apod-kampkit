package com.shadowings.apodkmp.android.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import org.koin.core.KoinComponent
import org.koin.core.get

class VerticalLayoutBuilder {
    fun build(): LinearLayout {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val parent = LinearLayout(container.ctx)
        parent.orientation = LinearLayout.VERTICAL
        children.forEach { parent.addView(it) }
        return parent
    }

    private var children: MutableList<View> = mutableListOf()

    fun image(block: (ImageBuilder.() -> Unit)? = null): AppCompatImageView {
        return if (block != null) {
            val view = ImageBuilder().apply(block).build()
            children.add(view)
            view
        } else {
            val view = ImageBuilder().build()
            children.add(view)
            view
        }
    }

    fun bigText(value: String = "", block: (BigTextBuilder.() -> Unit)? = null): AppCompatTextView {
        return if (block != null) {
            val view = BigTextBuilder().apply(block).build(value)
            children.add(view)
            view
        } else {
            val view = BigTextBuilder().build(value)
            children.add(view)
            view
        }
    }

    fun mediumText(value: String = "", block: (MediumTextBuilder.() -> Unit)? = null): AppCompatTextView {
        return if (block != null) {
            val view = MediumTextBuilder().apply(block).build(value)
            children.add(view)
            view
        } else {
            val view = MediumTextBuilder().build(value)
            children.add(view)
            view
        }
    }
}

class ImageBuilder {
    fun build(): AppCompatImageView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        return Views.buildImage(container.ctx)
    }
}

class BigTextBuilder {
    fun build(text: String = "", color: Int = Color.DKGRAY): AppCompatTextView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = AppCompatTextView(container.ctx)
        view.setTextColor(color)
        view.id = View.generateViewId()
        view.text = text
        view.elevation = 10.0F
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(Dimens.margin, Dimens.margin, Dimens.margin, Dimens.margin)
        view.layoutParams = lp
        view.textSize = Dimens.textSizeBig
        return view
    }
}

class MediumTextBuilder {
    fun build(text: String = "", color: Int = Color.DKGRAY): AppCompatTextView {
        val container = object : KoinComponent {
            val ctx: Context = get()
        }
        val view = AppCompatTextView(container.ctx)
        view.setTextColor(color)
        view.id = View.generateViewId()
        view.text = text
        view.elevation = 10.0F
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(Dimens.margin, Dimens.margin, Dimens.margin, Dimens.margin)
        view.layoutParams = lp
        view.textSize = Dimens.textSizeMedium
        return view
    }
}

fun verticalLayout(block: VerticalLayoutBuilder.() -> Unit): LinearLayout = VerticalLayoutBuilder().apply(block).build()

fun verticalScroll(block: ScrollView.() -> View): ScrollView {
    val container = object : KoinComponent {
        val ctx: Context = get()
    }
    val view = ScrollView(container.ctx)
    view.addView(block(view))
    return view
}
