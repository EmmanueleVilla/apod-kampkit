package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.shadowings.apodkmp.android.utils.Dimens
import com.shadowings.apodkmp.android.utils.appendBelowWithMarginAndHeight
import com.shadowings.apodkmp.model.Apod

class ImageDetailFrament(val apod: Apod) : BaseHighlightFragment() {

    private lateinit var title: AppCompatTextView
    private lateinit var description: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val set = initViews(context!!)

        title = AppCompatTextView(activity)
        title.id = View.generateViewId()
        title.setTextColor(Color.DKGRAY)
        title.textSize = Dimens.textSizeBig

        description = AppCompatTextView(activity)
        description.id = View.generateViewId()
        description.setTextColor(Color.DKGRAY)
        description.textSize = Dimens.textSizeMedium

        set.appendBelowWithMarginAndHeight(title, highlightImage, Dimens.margin, Dimens.textSizeBig.toInt())
        set.appendBelowWithMarginAndHeight(description, title, Dimens.margin, Dimens.textSizeMedium.toInt())

        loadImage(apod.url)
        title.text = apod.title
        description.text = apod.explanation

        return scrollView
    }
}
