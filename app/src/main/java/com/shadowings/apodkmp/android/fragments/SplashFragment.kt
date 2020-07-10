package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import co.touchlab.kampstarter.android.R
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalLayout
import com.shadowings.apodkmp.home.HomeInteractor

class SplashFragment : Fragment() {

    lateinit var nasa: ImageView
    lateinit var add: ImageView
    lateinit var kotlin: ImageView
    lateinit var constraintLayout: ConstraintLayout
    lateinit var imageContainer: ConstraintLayout
    private val homeInteractor: HomeInteractor = HomeInteractor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels / 2

        val view = verticalLayout(Gravity.CENTER) {
            verticalLayout(height = height, gravity = Gravity.CENTER) {
                image(height = height / 100 * 45) {
                    res = R.drawable.ic_nasa
                    scale = ImageView.ScaleType.CENTER_INSIDE
                }
                image(height = height / 100 * 10, width = height / 100 * 10) {
                    res = R.drawable.add
                    scale = ImageView.ScaleType.CENTER_INSIDE
                }
                image(height = height / 100 * 45) {
                    res = R.drawable.ic_kotlin
                    scale = ImageView.ScaleType.CENTER_INSIDE
                }
            }
        }

        homeInteractor.subscribe {
            if (activity != null && it.homeState.latest.isNotEmpty()) {
                (activity as MainActivity).openHome()
            }
        }

        homeInteractor.init()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        homeInteractor.unsubscribe()
    }
}
