package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.ConstraintPositions
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalLayout
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.verticalScroll
import com.shadowings.apodkmp.model.Apod

class ImageDetailFragment(private val apod: Apod, private val imageHeight: Int) : Fragment() {

    private lateinit var imageContainer: ConstraintLayout
    private lateinit var highlightImage: AppCompatImageView
    private lateinit var title: AppCompatTextView
    private lateinit var description: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = verticalScroll {
            verticalLayout {
                imageContainer = constraintLayout(height = imageHeight) {
                    highlightImage = image(position = ConstraintPositions.Center, margin = 0)
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
