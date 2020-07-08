package com.shadowings.apodkmp.android.fragments

import android.view.View
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    protected lateinit var scrollView: ScrollView
    protected lateinit var constraintLayout: ConstraintLayout

    open fun initViews(): ConstraintSet {
        scrollView = ScrollView(activity)
        scrollView.id = View.generateViewId()

        constraintLayout = ConstraintLayout(activity)
        constraintLayout.id = View.generateViewId()

        return ConstraintSet()
    }
}
