package com.shadowings.apodkmp.android.fragments

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.splash.SplashInteractor

class HomeFragment : Fragment() {

    private lateinit var image: AppCompatImageView
    private lateinit var title: AppCompatTextView
    lateinit var constraintLayout: ConstraintLayout
    private val splashInteractor: SplashInteractor = SplashInteractor()

    fun imageValueHolder(context: Context, resource: Drawable): PropertyValuesHolder {
        val displayMetrics = DisplayMetrics()
        (context as MainActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenW = displayMetrics.widthPixels

        val multiplier = 1.0f * screenW / resource.intrinsicWidth

        return PropertyValuesHolder.ofInt(
                "IMAGE_HEIGHT",
                image.height,
                (resource.intrinsicHeight * multiplier).toInt()
        )
    }

    fun updateListener(it: ValueAnimator) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.constrainHeight(image.id, it.getAnimatedValue("IMAGE_HEIGHT") as Int)
        constraintSet.applyTo(constraintLayout)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        constraintLayout = ConstraintLayout(activity)
        constraintLayout.id = View.generateViewId()

        image = AppCompatImageView(context)
        image.id = View.generateViewId()
        image.elevation = 9.0F

        title = AppCompatTextView(activity)
        title.id = View.generateViewId()
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.BOTTOM
        title.textSize = 32.0F
        title.elevation = 10.0F

        val set = ConstraintSet()
        set.connect(image.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START)
        set.connect(image.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        set.connect(image.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        set.constrainHeight(image.id, 1)

        set.connect(title.id, ConstraintSet.START, image.id, ConstraintSet.START, 12)
        set.connect(title.id, ConstraintSet.END, image.id, ConstraintSet.END, 12)
        set.connect(title.id, ConstraintSet.BOTTOM, image.id, ConstraintSet.BOTTOM, 12)
        set.connect(title.id, ConstraintSet.TOP, image.id, ConstraintSet.TOP, 12)

        constraintLayout.addView(image)
        constraintLayout.addView(title)
        constraintLayout.setConstraintSet(set)

        splashInteractor.subscribe {
            if (activity != null) {
                activity!!.runOnUiThread {
                    val apod = it.splashState.apod
                    Glide.with(this)
                            .load(apod.url)
                            .into(object : CustomViewTarget<ImageView, Drawable>(image) {
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
                                        addListener(object : Animator.AnimatorListener {
                                            override fun onAnimationRepeat(animation: Animator?) {
                                            }

                                            override fun onAnimationEnd(animation: Animator?) {
                                                title.text = apod.title
                                            }

                                            override fun onAnimationCancel(animation: Animator?) {
                                            }

                                            override fun onAnimationStart(animation: Animator?) {
                                            }
                                        })
                                    }
                                    valueAnimator.start()
                                    image.setImageDrawable(resource)
                                }
                            })
                }
            }
        }

        splashInteractor.init()

        return constraintLayout
    }
}
