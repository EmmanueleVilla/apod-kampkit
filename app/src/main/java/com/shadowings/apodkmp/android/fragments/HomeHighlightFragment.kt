package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampstarter.android.R
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.adapters.ApodAdapter
import com.shadowings.apodkmp.android.utils.dsl.ConstraintPositions
import com.shadowings.apodkmp.android.utils.dsl.Dimens
import com.shadowings.apodkmp.android.utils.dsl.verticalLayout
import com.shadowings.apodkmp.android.utils.dsl.verticalScroll
import com.shadowings.apodkmp.home.HomeInteractor
import com.shadowings.apodkmp.model.Apod

class HomeHighlightFragment : BaseHighlightFragment() {

    private lateinit var title: AppCompatTextView
    private lateinit var latestLabel: AppCompatTextView
    private lateinit var latest: RecyclerView
    private lateinit var logo: AppCompatImageView
    private val homeInteractor: HomeInteractor =
        HomeInteractor()
    private var apod: Apod = Apod()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val apodAdapter = ApodAdapter {
                image, apod ->
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            val multiplier = 1.0f * displayMetrics.widthPixels / image.drawable.intrinsicWidth
            (activity as MainActivity).openDetailFromFragment(apod, this, (image.drawable.intrinsicHeight * multiplier).toInt())
        }

        val view = verticalScroll {
            verticalLayout {
                imageContainer = constraintLayout(height = 500) {
                    highlightImage = image(position = ConstraintPositions.Center, margin = 0)
                    title = text(size = Dimens.textSizeBig, color = Color.WHITE, position = ConstraintPositions.Center)
                    logo = image(drawable = R.drawable.youtube_logo, width = Dimens.logo, height = Dimens.logo, position = ConstraintPositions.BottomStart)
                }
                latestLabel = text(value = "Latest:")
                horizontalRecycler(apodAdapter, height = Dimens.latestCardSize)
            }
        }

        imageContainer.setOnClickListener {
            (activity as MainActivity).openDetailFromFragment(apod, this, imageContainer.height)
        }

        homeInteractor.subscribe {
            if (activity != null && it.homeState.latest.isNotEmpty()) {
                requireActivity().runOnUiThread {
                    val apods = it.homeState.latest
                    this.apod = apods.first()

                    apodAdapter.apods = apods.subList(1, apods.size - 1)
                    apodAdapter.notifyDataSetChanged()

                    loadImage(apod.imageUrl)
                    title.text = apod.title
                    logo.visibility = if (apod.media_type == "video") View.VISIBLE else View.GONE
                }
            }
        }

        homeInteractor.init()

        return view
    }
}
