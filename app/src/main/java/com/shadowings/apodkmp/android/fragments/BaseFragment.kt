package com.shadowings.apodkmp.android.fragments

import android.content.Context
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.shadowings.apodkmp.android.utils.Views

open class BaseFragment : Fragment() {
    protected lateinit var scrollView: ScrollView
    protected lateinit var linearLayout: LinearLayout

    open fun initViews(context: Context) {
        scrollView = Views.buildScrollView(context)
        linearLayout = Views.buildLinearLayout(context)
        scrollView.addView(linearLayout)
    }
}
