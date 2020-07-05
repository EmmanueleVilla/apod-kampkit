package co.touchlab.kampstarter.android.fragments

import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import co.touchlab.kampstarter.android.R
import co.touchlab.kampstarter.splash.SplashInteractor

class SplashFragment : Fragment() {

    lateinit var nasa : ImageView
    lateinit var kotlin : ImageView
    lateinit var container : ConstraintLayout
    private val splashInteractor : SplashInteractor = SplashInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container = ConstraintLayout(context)
        container.id = View.generateViewId()
        container.setBackgroundColor(Color.BLUE)

        nasa = ImageView(context)
        nasa.id = View.generateViewId()
        nasa.scaleType = ImageView.ScaleType.CENTER_INSIDE
        nasa.setImageResource(R.drawable.nasa_logo)
        container.addView(nasa)

        kotlin = ImageView(context)
        kotlin.id = View.generateViewId()
        kotlin.scaleType = ImageView.ScaleType.CENTER_INSIDE
        kotlin.setImageResource(R.drawable.kotlin_logo)
        container.addView(kotlin)

        val set = ConstraintSet()
        set.connect(nasa.id, ConstraintSet.START, container.id, ConstraintSet.START)
        set.connect(nasa.id, ConstraintSet.END, container.id, ConstraintSet.END)
        set.connect(nasa.id, ConstraintSet.TOP, container.id, ConstraintSet.TOP)
        set.constrainHeight(nasa.id, 200)

        set.connect(kotlin.id, ConstraintSet.BOTTOM, container.id, ConstraintSet.BOTTOM)
        set.connect(kotlin.id, ConstraintSet.END, container.id, ConstraintSet.END)
        set.connect(kotlin.id, ConstraintSet.TOP, container.id, ConstraintSet.TOP)
        set.constrainHeight(kotlin.id, 200)

        container.setConstraintSet(set)

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
    }
}