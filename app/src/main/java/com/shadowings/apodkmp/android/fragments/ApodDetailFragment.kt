package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import co.touchlab.kampstarter.android.R
import com.bumptech.glide.Glide
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.ConstraintPositions
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalLayout
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalScroll
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import com.shadowings.apodkmp.model.Apod

class ApodDetailFragment(private val apod: Apod, private val imageHeight: Int) : Fragment() {

    private lateinit var imageContainer: ConstraintLayout
    private lateinit var highlightImage: AppCompatImageView
    private lateinit var title: AppCompatTextView
    private lateinit var description: AppCompatTextView
    private lateinit var fullscreen: AppCompatImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val fragment = this
        val view = verticalScroll {
            verticalLayout {
                imageContainer = constraintLayout(height = imageHeight) {
                    highlightImage = image(position = ConstraintPositions.Center, margin = 0)
                    fullscreen = image(width = Dimens.logo, height = Dimens.logo, position = ConstraintPositions.BottomEnd) {
                        res = R.drawable.baseline_fullscreen_white_48
                        clickListener = {
                            (activity as MainActivity).openPlayer(apod, fragment, highlightImage)
                        }
                    }
                }
                title = text()
                description = text()
            }
        }

        highlightImage.transitionName = apod.date

        Glide.with(this)
            .load(apod.imageUrl)
            .into(highlightImage)

        title.text = apod.title
        description.text = apod.explanation
        return view
    }
}
