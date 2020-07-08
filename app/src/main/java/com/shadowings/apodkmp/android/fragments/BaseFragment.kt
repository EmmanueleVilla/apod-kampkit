package com.shadowings.apodkmp.android.fragments

import android.content.Context
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.shadowings.apodkmp.android.utils.Views

open class BaseFragment : Fragment() {
    protected lateinit var scrollView: ScrollView
    protected lateinit var constraintLayout: ConstraintLayout

    open fun initViews(context: Context): ConstraintSet {
        scrollView = Views.buildScrollView(context)
        constraintLayout = Views.buildConstraintLayout(context)
        return ConstraintSet()
    }
}
