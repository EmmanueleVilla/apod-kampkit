package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintSet
import com.shadowings.apodkmp.android.utils.Dimens
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

        set.connect(title.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START, Dimens.margin)
        set.connect(title.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END, Dimens.margin)
        set.connect(title.id, ConstraintSet.TOP, highlightImage.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.constrainHeight(title.id, Dimens.textSizeBig.toInt())

        set.connect(description.id, ConstraintSet.START, constraintLayout.id, ConstraintSet.START, Dimens.margin)
        set.connect(description.id, ConstraintSet.END, constraintLayout.id, ConstraintSet.END, Dimens.margin)
        set.connect(description.id, ConstraintSet.TOP, title.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.constrainHeight(description.id, Dimens.textSizeMedium.toInt())

        loadImage(apod.url)
        title.text = apod.title
        description.text = apod.explanation

        return scrollView
    }
}
