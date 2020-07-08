package com.shadowings.apodkmp.android.utils

import android.content.Context
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Views {
    companion object {
        fun buildScrollView(context: Context): ScrollView {
            val view = ScrollView(context)
            view.id = View.generateViewId()
            return view
        }

        fun buildConstraintLayout(context: Context): ConstraintLayout {
            val view = ConstraintLayout(context)
            view.id = View.generateViewId()
            return view
        }

        fun buildImage(context: Context, visibility: Int = View.VISIBLE): AppCompatImageView {
            val view = AppCompatImageView(context)
            view.id = View.generateViewId()
            view.visibility = visibility
            view.elevation = 9.0F
            return view
        }

        fun buildMediumText(context: Context, color: Int): AppCompatTextView {
            val view = buildText(context, color)
            view.textSize = Dimens.textSizeMedium
            return view
        }

        fun buildBigText(context: Context, color: Int): AppCompatTextView {
            val view = buildText(context, color)
            view.textSize = Dimens.textSizeBig
            return view
        }

        fun buildHorizontalRecyclerView(context: Context): RecyclerView {
            val manager = LinearLayoutManager(context)
            manager.orientation = RecyclerView.HORIZONTAL
            val view = RecyclerView(context)
            view.id = View.generateViewId()
            view.layoutManager = manager
            return view
        }

        private fun buildText(context: Context, color: Int): AppCompatTextView {
            val view = AppCompatTextView(context)
            view.setTextColor(color)
            view.id = View.generateViewId()
            view.elevation = 10.0F
            return view
        }
    }
}
