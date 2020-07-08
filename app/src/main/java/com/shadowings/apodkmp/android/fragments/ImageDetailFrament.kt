package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.shadowings.apodkmp.android.utils.Views
import com.shadowings.apodkmp.model.Apod

class ImageDetailFrament(val apod: Apod) : BaseHighlightFragment() {

    private lateinit var title: AppCompatTextView
    private lateinit var description: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViews(context!!)

        title = Views.buildBigText(context!!, Color.WHITE)
        title.setTextColor(Color.DKGRAY)

        description = Views.buildMediumText(context!!, Color.WHITE)
        description.setTextColor(Color.DKGRAY)

        linearLayout.addView(title)
        linearLayout.addView(description)

        loadImage(apod.url)
        title.text = apod.title
        description.text = apod.explanation

        return scrollView
    }
}
