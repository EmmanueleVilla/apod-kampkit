package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampstarter.android.R
import com.shadowings.apodkmp.android.adapters.ApodAdapter
import com.shadowings.apodkmp.android.utils.Dimens
import com.shadowings.apodkmp.home.HomeInteractor

class HomeHighlightFragment : BaseHighlightFragment() {

    private lateinit var title: AppCompatTextView
    private lateinit var latestLabel: AppCompatTextView
    private lateinit var latest: RecyclerView
    private lateinit var logo: AppCompatImageView
    private val homeInteractor: HomeInteractor =
        HomeInteractor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val set = initViews(context!!)

        logo = AppCompatImageView(context)
        with(logo) {
            id = View.generateViewId()
            setBackgroundResource(R.drawable.youtube_logo)
            elevation = 10.0F
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            visibility = View.GONE
        }

        title = AppCompatTextView(activity)
        with(title) {
            setTextColor(Color.WHITE)
            id = View.generateViewId()
            gravity = Gravity.TOP
            textSize = Dimens.textSizeBig
            elevation = 10.0F
        }

        latestLabel = AppCompatTextView(activity)
        with(latestLabel) {
            id = View.generateViewId()
            setTextColor(Color.DKGRAY)
            gravity = Gravity.BOTTOM
            textSize = Dimens.textSizeMedium
            text = "Latest:"
        }

        val manager = LinearLayoutManager(activity)
        manager.orientation = RecyclerView.HORIZONTAL
        latest = RecyclerView(activity!!)

        with(latest) {
            id = View.generateViewId()
            layoutManager = manager
            adapter = ApodAdapter()
        }

        set.connect(title.id, ConstraintSet.START, highlightImage.id, ConstraintSet.START, Dimens.margin)
        set.connect(title.id, ConstraintSet.END, highlightImage.id, ConstraintSet.END, Dimens.margin)
        set.connect(title.id, ConstraintSet.BOTTOM, highlightImage.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.connect(title.id, ConstraintSet.TOP, highlightImage.id, ConstraintSet.TOP, Dimens.margin)

        set.connect(logo.id, ConstraintSet.BOTTOM, highlightImage.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.connect(logo.id, ConstraintSet.START, highlightImage.id, ConstraintSet.START, Dimens.margin)
        set.constrainHeight(logo.id, 200)
        set.constrainWidth(logo.id, 200)

        set.connect(latestLabel.id, ConstraintSet.START, highlightImage.id, ConstraintSet.START, Dimens.margin)
        set.connect(latestLabel.id, ConstraintSet.END, highlightImage.id, ConstraintSet.END, Dimens.margin)
        set.connect(latestLabel.id, ConstraintSet.TOP, highlightImage.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.constrainHeight(latestLabel.id, (Dimens.margin + Dimens.textSizeMedium + Dimens.margin).toInt())

        set.connect(latest.id, ConstraintSet.START, highlightImage.id, ConstraintSet.START, 0)
        set.connect(latest.id, ConstraintSet.END, highlightImage.id, ConstraintSet.END, 0)
        set.connect(latest.id, ConstraintSet.TOP, latestLabel.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.connect(latest.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.constrainHeight(latest.id, Dimens.latestCardSize)

        with(constraintLayout) {
            addView(highlightImage)
            addView(title)
            addView(logo)
            addView(latestLabel)
            addView(latest)
            setConstraintSet(set)
        }

        scrollView.addView(constraintLayout)

        homeInteractor.subscribe {
            if (activity != null && it.homeState.latest.isNotEmpty()) {
                activity!!.runOnUiThread {
                    val apods = it.homeState.latest

                    (latest.adapter as ApodAdapter).apods = apods.subList(1, apods.size - 1)
                    (latest.adapter as ApodAdapter).notifyDataSetChanged()

                    val apod = apods.first()
                    logo.visibility = if (apod.media_type == "video") View.VISIBLE else View.GONE
                    title.text = apod.title
                    loadImage(apod.url)
                }
            }
        }

        homeInteractor.init()

        return scrollView
    }
}
