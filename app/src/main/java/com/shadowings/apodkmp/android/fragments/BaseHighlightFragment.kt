package com.shadowings.apodkmp.android.fragments

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.utils.Views
import com.shadowings.apodkmp.android.utils.connectToTopWithHeight

open class BaseHighlightFragment : BaseFragment() {

    companion object {
        const val IMAGE_HEIGHT: String = "IMAGE_HEIGHT"
    }

    protected lateinit var highlightImage: AppCompatImageView

    override fun initViews(context: Context): ConstraintSet {
        val set = super.initViews(context)
        highlightImage = Views.buildImage(context)

        val displayMetrics = DisplayMetrics()
        (context as MainActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenH = displayMetrics.heightPixels
        set.connectToTopWithHeight(highlightImage, constraintLayout, screenH / 2)
        return set
    }

    fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(object : CustomViewTarget<ImageView, Drawable>(highlightImage) {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    val valueAnimator =
                        ValueAnimator.ofPropertyValuesHolder(
                            imageValueHolder(context!!, resource)
                        )

                    with(valueAnimator) {
                        interpolator = DecelerateInterpolator()
                        duration = 300
                        addUpdateListener { updateListener(it) }
                    }
                    valueAnimator.start()
                    highlightImage.setImageDrawable(resource)
                }
            })
    }

    fun imageValueHolder(context: Context, resource: Drawable): PropertyValuesHolder {
        val displayMetrics = DisplayMetrics()
        (context as MainActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenW = displayMetrics.widthPixels

        val multiplier = 1.0f * screenW / resource.intrinsicWidth

        return PropertyValuesHolder.ofInt(
            IMAGE_HEIGHT,
            highlightImage.height,
            (resource.intrinsicHeight * multiplier).toInt()
        )
    }

    fun updateListener(it: ValueAnimator) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.constrainHeight(highlightImage.id, it.getAnimatedValue(IMAGE_HEIGHT) as Int)
        constraintSet.applyTo(constraintLayout)
    }
}
