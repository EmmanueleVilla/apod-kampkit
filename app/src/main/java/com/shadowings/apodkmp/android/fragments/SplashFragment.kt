package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import co.touchlab.kampstarter.android.R
import com.shadowings.apodkmp.splash.SplashInteractor

class SplashFragment : Fragment() {

    lateinit var nasa: ImageView
    lateinit var add: ImageView
    lateinit var kotlin: ImageView
    lateinit var constraintLayout: ConstraintLayout
    lateinit var imageContainer: ConstraintLayout
    private val splashInteractor: SplashInteractor = SplashInteractor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels

        constraintLayout = ConstraintLayout(context)
        constraintLayout.id = View.generateViewId()

        imageContainer = ConstraintLayout(context)
        imageContainer.id = View.generateViewId()
        constraintLayout.addView(imageContainer)

        nasa = ImageView(context)
        nasa.id = View.generateViewId()
        nasa.scaleType = ImageView.ScaleType.CENTER_INSIDE
        nasa.setImageResource(R.drawable.nasa_logo)
        imageContainer.addView(nasa)

        add = ImageView(context)
        add.id = View.generateViewId()
        add.scaleType = ImageView.ScaleType.CENTER_INSIDE
        add.setImageResource(R.drawable.add)
        imageContainer.addView(add)

        kotlin = ImageView(context)
        kotlin.id = View.generateViewId()
        kotlin.scaleType = ImageView.ScaleType.CENTER_INSIDE
        kotlin.setImageResource(R.drawable.kotlin_logo)
        imageContainer.addView(kotlin)

        val half = height / 2

        val constraintLayoutSet = ConstraintSet()
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM)
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START)
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        constraintLayoutSet.constrainHeight(imageContainer.id, half)

        constraintLayout.setConstraintSet(constraintLayoutSet)

        val imageContainerSet = ConstraintSet()
        imageContainerSet.connect(nasa.id, ConstraintSet.TOP, imageContainer.id, ConstraintSet.TOP)
        imageContainerSet.connect(nasa.id, ConstraintSet.START, imageContainer.id, ConstraintSet.START)
        imageContainerSet.connect(nasa.id, ConstraintSet.END, imageContainer.id, ConstraintSet.END)
        imageContainerSet.constrainHeight(nasa.id, half / 100 * 45)

        imageContainerSet.connect(add.id, ConstraintSet.TOP, nasa.id, ConstraintSet.BOTTOM)
        imageContainerSet.connect(add.id, ConstraintSet.START, imageContainer.id, ConstraintSet.START)
        imageContainerSet.connect(add.id, ConstraintSet.END, imageContainer.id, ConstraintSet.END)
        imageContainerSet.connect(add.id, ConstraintSet.BOTTOM, kotlin.id, ConstraintSet.TOP)
        imageContainerSet.constrainHeight(add.id, half / 100 * 10)

        imageContainerSet.connect(kotlin.id, ConstraintSet.TOP, add.id, ConstraintSet.BOTTOM)
        imageContainerSet.connect(kotlin.id, ConstraintSet.START, imageContainer.id, ConstraintSet.START)
        imageContainerSet.connect(kotlin.id, ConstraintSet.END, imageContainer.id, ConstraintSet.END)
        imageContainerSet.connect(kotlin.id, ConstraintSet.BOTTOM, imageContainer.id, ConstraintSet.BOTTOM)
        imageContainerSet.constrainHeight(kotlin.id, half / 100 * 45)

        imageContainer.setConstraintSet(imageContainerSet)

        splashInteractor.subscribe {
            /*
            runOnUiThread {
                val apod = it.splashState.apod
                description.text = apod.explanation
                Glide.with(this).load(apod.url).into(image)
            }
             */
        }

        splashInteractor.init()
        return constraintLayout
    }
}
