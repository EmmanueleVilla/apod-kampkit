package co.touchlab.kampstarter.android.fragments

import android.graphics.Color
import android.media.Image
import android.os.Bundle
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
    private val splashInteractor : SplashInteractor = SplashInteractor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        constraintLayout = ConstraintLayout(context)
        constraintLayout.id = View.generateViewId()
        constraintLayout.setBackgroundColor(Color.BLUE)

        nasa = ImageView(context)
        nasa.id = View.generateViewId()
        nasa.scaleType = ImageView.ScaleType.CENTER_INSIDE
        nasa.setImageResource(R.drawable.nasa_logo)
        constraintLayout.addView(nasa)

        kotlin = ImageView(context)
        kotlin.id = View.generateViewId()
        kotlin.scaleType = ImageView.ScaleType.CENTER_INSIDE
        kotlin.setImageResource(R.drawable.kotlin_logo)
        constraintLayout.addView(kotlin)

        val set = ConstraintSet()
        set.connect(nasa.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START)
        set.connect(nasa.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        set.connect(nasa.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        set.constrainHeight(nasa.id, 200)

        set.connect(kotlin.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM)
        set.connect(kotlin.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        set.connect(kotlin.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        set.constrainHeight(kotlin.id, 200)

        constraintLayout.setConstraintSet(set)

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