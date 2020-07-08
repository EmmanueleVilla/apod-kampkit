package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.shadowings.apodkmp.android.utils.Views
import com.shadowings.apodkmp.model.Apod

class ImageDetailFrament(private val apod: Apod, private val imageHeight: Int) : BaseHighlightFragment() {

    private lateinit var title: AppCompatTextView
    private lateinit var description: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViews(requireContext(), imageHeight)

        title = Views.buildBigText(requireContext(), Color.WHITE)
        title.setTextColor(Color.DKGRAY)

        description = Views.buildMediumText(requireContext(), Color.WHITE)
        description.setTextColor(Color.DKGRAY)

        linearLayout.addView(title)
        linearLayout.addView(description)

        loadImage(apod.url)
        title.text = apod.title
        description.text = apod.explanation

        return scrollView
    }
}
