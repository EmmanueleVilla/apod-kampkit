package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.shadowings.apodkmp.splash.SplashInteractor

class HomeFragment : Fragment() {

    private lateinit var image: ImageView
    private lateinit var description: TextView
    lateinit var constraintLayout: ConstraintLayout
    private val splashInteractor: SplashInteractor = SplashInteractor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        constraintLayout = ConstraintLayout(activity)
        constraintLayout.id = View.generateViewId()

        image = ImageView(activity)
        image.id = View.generateViewId()

        description = TextView(activity)
        description.id = View.generateViewId()

        val set = ConstraintSet()
        set.connect(image.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START)
        set.connect(image.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END)
        set.connect(image.id, ConstraintSet.TOP, constraintLayout.id, ConstraintSet.TOP)
        set.constrainHeight(image.id, 200)

        set.connect(description.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START, 12)
        set.connect(description.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END, 12)
        set.connect(description.id, ConstraintSet.TOP, image.id, ConstraintSet.BOTTOM, 12)
        set.connect(description.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM, 12)

        constraintLayout.addView(image)
        constraintLayout.addView(description)
        constraintLayout.setConstraintSet(set)
        constraintLayout.setBackgroundColor(Color.CYAN)

        splashInteractor.subscribe {
            if (activity != null) {
                activity!!.runOnUiThread {
                    val apod = it.splashState.apod
                    description.text = apod.explanation
                    Glide.with(this).load(apod.url).into(image)
                }
            }
        }

        splashInteractor.init()

        return constraintLayout
    }
}
