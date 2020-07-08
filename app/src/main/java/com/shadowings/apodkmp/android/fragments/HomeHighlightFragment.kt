package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadowings.apodkmp.android.utils.verticalLayout
import com.shadowings.apodkmp.android.utils.verticalScroll
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

        val view = verticalScroll {
            verticalLayout {
                highlightImage = image()
                title = bigText()
            }
        }

        homeInteractor.subscribe {
            if (activity != null && it.homeState.latest.isNotEmpty()) {
                requireActivity().runOnUiThread {

                    val apods = it.homeState.latest
                    this.apod = apods.first()

                    /*
                    (latest.adapter as ApodAdapter).apods = apods.subList(1, apods.size - 1)
                    (latest.adapter as ApodAdapter).notifyDataSetChanged()

                    logo.visibility = View.GONE
                    logo.visibility = if (apod.media_type == "video") View.VISIBLE else View.GONE
                    title.text = apod.title

                    loadImage(apod.url)

                     */

                    Glide.with(this)
                            .load(apod.imageUrl)
                            .into(highlightImage)

                    title.text = apod.title
                }
            }
        }

        homeInteractor.init()

        return view
/*
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        initViews(requireContext(), displayMetrics.heightPixels / 2)

        highlightImage.setOnClickListener {
            (activity as MainActivity).openDetailFromFragment(apod, this, highlightImage.height)
        }

        logo = Views.buildImage(context!!, View.GONE)
        logo.setBackgroundResource(R.drawable.youtube_logo)
        logo.scaleType = ImageView.ScaleType.CENTER_INSIDE

        title = Views.buildBigText(context!!, Color.WHITE)
        title.gravity = Gravity.TOP

        latestLabel = Views.buildMediumText(context!!, Color.DKGRAY)
        latestLabel.gravity = Gravity.BOTTOM
        latestLabel.text = "Latest:"

        latest = Views.buildHorizontalRecyclerView(context!!)
        latest.adapter = ApodAdapter { image, apod ->
            val multiplier = 1.0f * displayMetrics.widthPixels / image.drawable.intrinsicWidth
            (activity as MainActivity).openDetailFromFragment(apod, this, (image.drawable.intrinsicHeight * multiplier).toInt())
        }

        imageContainer.addView(title)
        imageContainer.addView(logo)

        imageContainerSet.centerInParentWithMargin(title, highlightImage, Dimens.margin)
        imageContainerSet.bottomStartInParentWithSide(logo, highlightImage, 200)
        imageContainerSet.applyTo(imageContainer)

        linearLayout.addView(latestLabel)
        linearLayout.addView(latest)



        return scrollView

 */
    }
}
