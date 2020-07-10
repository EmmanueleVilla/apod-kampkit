package com.shadowings.apodkmp.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import co.touchlab.kampstarter.android.R
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.adapters.ApodAdapter
import com.shadowings.apodkmp.android.animations.loadImageAnimated
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.ConstraintPositions
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalLayout
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalScroll
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import com.shadowings.apodkmp.home.HomeInteractor
import com.shadowings.apodkmp.model.Apod

class HomeHighlightFragment : Fragment() {

    private lateinit var imageContainer: ConstraintLayout
    private lateinit var highlightImage: AppCompatImageView
    private lateinit var title: AppCompatTextView
    private lateinit var logo: AppCompatImageView
    private val homeInteractor: HomeInteractor =
        HomeInteractor()
    private var apod: Apod = Apod()
    private var content: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (content != null) {
            return content
        }

        val apodAdapter = ApodAdapter {
                image, apod ->
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            val multiplier = 1.0f * displayMetrics.widthPixels / image.drawable.intrinsicWidth
            image.transitionName = apod.date
            (activity as MainActivity).openDetailWithTransaction(
                apod,
                this,
                image,
                (image.drawable.intrinsicHeight * multiplier).toInt()
            )
        }

        content = verticalScroll {
            verticalLayout {
                imageContainer = constraintLayout(height = 500) {
                    highlightImage = image(position = ConstraintPositions.Center, margin = 0)
                    title = text(position = ConstraintPositions.Center) {
                        size = Dimens.textSizeBig
                        color = Color.WHITE
                    }
                    logo = image(
                        width = Dimens.logo,
                        height = Dimens.logo,
                        position = ConstraintPositions.BottomStart
                    ) {
                        res = R.drawable.youtube_logo
                    }
                }
                text(bottomMargin = 0) {
                    label = "Latest:"
                }
                horizontalRecycler(apodAdapter, height = Dimens.latestCardSize)
            }
        }

        imageContainer.setOnClickListener {
            (activity as MainActivity).openDetailWithHeight(apod, imageContainer.height)
        }

        homeInteractor.subscribe {
            if (activity != null && it.homeState.latest.isNotEmpty()) {
                requireActivity().runOnUiThread {
                    val apods = it.homeState.latest
                    this.apod = apods.first()

                    highlightImage.transitionName = apod.date

                    apodAdapter.apods = apods.subList(1, apods.size - 1)
                    apodAdapter.notifyDataSetChanged()

                    highlightImage.loadImageAnimated(imageContainer, apod.imageUrl)
                    title.text = apod.title
                    logo.visibility = if (apod.media_type == "video") View.VISIBLE else View.GONE
                }
            }
        }

        homeInteractor.init()

        return content
    }
}
