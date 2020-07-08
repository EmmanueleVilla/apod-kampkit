package com.shadowings.apodkmp.android.utils

import android.content.Context
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

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

        fun buildImage(context: Context): AppCompatImageView {
            val view = AppCompatImageView(context)
            view.id = View.generateViewId()
            view.elevation = 9.0F
            return view
        }
    }
}
