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
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.adapters.ApodAdapter
import com.shadowings.apodkmp.home.HomeInteractor

class HomeFragment : Fragment() {

    private lateinit var image: AppCompatImageView
    private lateinit var title: AppCompatTextView
    private lateinit var latestLabel: AppCompatTextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var scrollView: ScrollView
    private lateinit var latest: RecyclerView
    private val homeInteractor: HomeInteractor =
        HomeInteractor()

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
        scrollView = ScrollView(activity)
        scrollView.id = View.generateViewId()

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

        latestLabel = AppCompatTextView(activity)
        latestLabel.id = View.generateViewId()
        latestLabel.setTextColor(Color.DKGRAY)
        latestLabel.gravity = Gravity.BOTTOM
        latestLabel.textSize = 24.0F
        latestLabel.text = "Latest:"

        latest = RecyclerView(activity!!)
        latest.id = View.generateViewId()
        val manager = LinearLayoutManager(activity)
        manager.orientation = RecyclerView.HORIZONTAL
        latest.layoutManager = manager
        latest.adapter = ApodAdapter()

        val set = ConstraintSet()
        set.connect(image.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START)
        set.connect(image.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        set.connect(image.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        set.constrainHeight(image.id, 1)

        set.connect(title.id, ConstraintSet.START, image.id, ConstraintSet.START, 0)
        set.connect(title.id, ConstraintSet.END, image.id, ConstraintSet.END, 0)
        set.connect(title.id, ConstraintSet.BOTTOM, image.id, ConstraintSet.BOTTOM, 0)
        set.connect(title.id, ConstraintSet.TOP, image.id, ConstraintSet.TOP, 0)

        set.connect(latestLabel.id, ConstraintSet.START, image.id, ConstraintSet.START, 0)
        set.connect(latestLabel.id, ConstraintSet.END, image.id, ConstraintSet.END, 0)
        set.connect(latestLabel.id, ConstraintSet.TOP, image.id, ConstraintSet.BOTTOM, 24)
        set.constrainHeight(latestLabel.id, 24)

        set.connect(latest.id, ConstraintSet.START, image.id, ConstraintSet.START, 0)
        set.connect(latest.id, ConstraintSet.END, image.id, ConstraintSet.END, 0)
        set.connect(latest.id, ConstraintSet.TOP, latestLabel.id, ConstraintSet.BOTTOM, 0)
        set.connect(latest.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM, 0)
        set.constrainHeight(latest.id, 325)

        constraintLayout.addView(image)
        constraintLayout.addView(title)
        constraintLayout.addView(latestLabel)
        constraintLayout.addView(latest)
        constraintLayout.setConstraintSet(set)

        scrollView.addView(constraintLayout)

        homeInteractor.subscribe {
            if (activity != null && it.homeState.latest.isNotEmpty()) {
                activity!!.runOnUiThread {
                    val apods = it.homeState.latest

                    (latest.adapter as ApodAdapter).apods = apods.subList(1, apods.size - 1)
                    (latest.adapter as ApodAdapter).notifyDataSetChanged()

                    val apod = apods.first()
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

        homeInteractor.init()

        return scrollView
    }
}
