package com.shadowings.apodkmp.android.fragments

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.shadowings.apodkmp.android.utils.dsl.heightValueHolder
import com.shadowings.apodkmp.android.utils.dsl.updateListener

open class BaseHighlightFragment : BaseFragment() {

    companion object {
        const val IMAGE_HEIGHT: String = "IMAGE_HEIGHT"
    }

    protected lateinit var imageContainer: ConstraintLayout
    protected lateinit var highlightImage: AppCompatImageView

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
                            heightValueHolder(context!!, resource, highlightImage)
                        )

                    with(valueAnimator) {
                        interpolator = DecelerateInterpolator()
                        duration = 250
                        addUpdateListener { updateListener(it, imageContainer) }
                    }
                    valueAnimator.start()
                    highlightImage.setImageDrawable(resource)
                }
            })
    }
}
