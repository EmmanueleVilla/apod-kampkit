package com.shadowings.apodkmp.android.utils.dsl

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.MainApp
import com.shadowings.apodkmp.android.fragments.BaseHighlightFragment

fun heightValueHolder(context: Context, resource: Drawable, image: ImageView): PropertyValuesHolder {
    val displayMetrics = DisplayMetrics()
    when (context) {
        is MainActivity -> context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        is MainApp -> context.activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    }
    val screenW = displayMetrics.widthPixels

    val multiplier = 1.0f * screenW / resource.intrinsicWidth

    return PropertyValuesHolder.ofInt(
        BaseHighlightFragment.IMAGE_HEIGHT,
        image.height,
        (resource.intrinsicHeight * multiplier).toInt()
    )
}

fun updateListener(it: ValueAnimator, view: View) {
    val lp = view.layoutParams
    lp.height = it.getAnimatedValue(BaseHighlightFragment.IMAGE_HEIGHT) as Int
    view.layoutParams = lp
}
