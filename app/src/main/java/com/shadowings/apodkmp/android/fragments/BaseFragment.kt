package com.shadowings.apodkmp.android.fragments

import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    protected lateinit var scrollView: ScrollView
    protected lateinit var linearLayout: LinearLayout
}
