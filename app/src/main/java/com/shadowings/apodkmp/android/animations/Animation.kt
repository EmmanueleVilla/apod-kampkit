package com.shadowings.apodkmp.android.animations

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.MainApp

const val IMAGE_HEIGHT: String = "IMAGE_HEIGHT"

fun heightValueHolder(context: Context, resource: Drawable, image: ImageView): PropertyValuesHolder {
    val displayMetrics = DisplayMetrics()
    when (context) {
        is MainActivity -> context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        is MainApp -> context.activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    }
    val screenW = displayMetrics.widthPixels

    val multiplier = 1.0f * screenW / resource.intrinsicWidth

    return PropertyValuesHolder.ofInt(
        IMAGE_HEIGHT,
        image.height,
        (resource.intrinsicHeight * multiplier).toInt()
    )
}

fun updateListener(it: ValueAnimator, view: View) {
    val lp = view.layoutParams
    lp.height = it.getAnimatedValue(IMAGE_HEIGHT) as Int
    view.layoutParams = lp
}

fun AppCompatImageView.loadImageAnimated(container: View, url: String) {
    val image = this
    Glide.with(image)
        .load(url)
        .into(object : CustomViewTarget<ImageView, Drawable>(image) {
            override fun onLoadFailed(errorDrawable: Drawable?) {
            }

            override fun onResourceCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                val valueAnimator =
                    ValueAnimator.ofPropertyValuesHolder(
                        heightValueHolder(
                            context!!,
                            resource,
                            image
                        )
                    )

                with(valueAnimator) {
                    interpolator = DecelerateInterpolator()
                    duration = 250
                    addUpdateListener {
                        updateListener(
                            it,
                            container
                        )
                    }
                }
                valueAnimator.start()
                image.setImageDrawable(resource)
            }
        })
}
