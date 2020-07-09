package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.shadowings.apodkmp.android.utils.dsl.ConstraintPositions
import com.shadowings.apodkmp.android.utils.dsl.verticalLayout
import com.shadowings.apodkmp.android.utils.dsl.verticalScroll
import com.shadowings.apodkmp.model.Apod

class ImageDetailFrament(private val apod: Apod, private val imageHeight: Int) : BaseHighlightFragment() {

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
        Glide.with(this)
            .load(apod.imageUrl)
            .into(highlightImage)

        title.text = apod.title
        description.text = apod.explanation
        return view
    }
}
