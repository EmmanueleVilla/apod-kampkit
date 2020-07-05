package co.touchlab.kampstarter.android.fragments

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
import co.touchlab.kampstarter.splash.SplashInteractor


class SplashFragment : Fragment() {

    lateinit var nasa : ImageView
    lateinit var kotlin : ImageView
    lateinit var constraintLayout: ConstraintLayout
    lateinit var imageContainer: ConstraintLayout
    private val splashInteractor : SplashInteractor = SplashInteractor()

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

        kotlin = ImageView(context)
        kotlin.id = View.generateViewId()
        kotlin.scaleType = ImageView.ScaleType.CENTER_INSIDE
        kotlin.setImageResource(R.drawable.kotlin_logo)
        imageContainer.addView(kotlin)

        val constraintLayoutSet = ConstraintSet()
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM)
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START)
        constraintLayoutSet.connect(imageContainer.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        constraintLayoutSet.constrainHeight(imageContainer.id, height/2)

        constraintLayout.setConstraintSet(constraintLayoutSet)

        val imageContainerSet = ConstraintSet()
        imageContainerSet.connect(nasa.id, ConstraintSet.TOP, imageContainer.id, ConstraintSet.TOP)
        imageContainerSet.connect(nasa.id, ConstraintSet.START, imageContainer.id, ConstraintSet.START)
        imageContainerSet.connect(nasa.id, ConstraintSet.END, imageContainer.id, ConstraintSet.END)
        imageContainerSet.constrainHeight(nasa.id, height/4)

        imageContainerSet.connect(kotlin.id, ConstraintSet.TOP, nasa.id, ConstraintSet.BOTTOM)
        imageContainerSet.connect(kotlin.id, ConstraintSet.START, imageContainer.id, ConstraintSet.START)
        imageContainerSet.connect(kotlin.id, ConstraintSet.END, imageContainer.id, ConstraintSet.END)
        imageContainerSet.connect(kotlin.id, ConstraintSet.BOTTOM, imageContainer.id, ConstraintSet.BOTTOM)
        imageContainerSet.constrainHeight(kotlin.id, height/4)

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