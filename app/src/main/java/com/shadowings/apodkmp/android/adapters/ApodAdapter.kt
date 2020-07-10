package com.shadowings.apodkmp.android.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampstarter.android.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.ConstraintPositions
import com.shadowings.apodkmp.android.dsl.builder.viewgroups.constraintLayout
import com.shadowings.apodkmp.android.dsl.constants.Dimens
import com.shadowings.apodkmp.model.Apod

class ApodAdapter(var clickListener: (image: AppCompatImageView, apod: Apod) -> Unit) : RecyclerView.Adapter<ApodViewHolder>() {

    var apods: List<Apod> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        var image: AppCompatImageView? = null
        var logo: AppCompatImageView? = null

        val view = constraintLayout(
            height = Dimens.latestCardSize,
            width = Dimens.latestCardSize
        ) {
            image = image(
                position = ConstraintPositions.Center,
                scaleType = ImageView.ScaleType.CENTER_CROP,
                backgroundColor = Color.GRAY
            )
            logo = image(
                drawable = R.drawable.youtube_logo,
                width = Dimens.logo,
                height = Dimens.logo,
                position = ConstraintPositions.BottomStart,
                margin = Dimens.margin * 2
            )
        }
        logo!!.elevation = 12.0F
        return ApodViewHolder(view, image!!, logo!!, clickListener)
    }

    override fun getItemCount(): Int {
        return apods.size
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        holder.setData(apods[position])
    }
}

class ApodViewHolder(
    private val container: ConstraintLayout,
    private val image: AppCompatImageView,
    private val logo: AppCompatImageView,
    val clickListener: (image: AppCompatImageView, apod: Apod) -> Unit
) :
    RecyclerView.ViewHolder(container) {
    fun setData(apod: Apod) {
        if (apod.imageUrl != "") {
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

            if (apod.imageAspectRatio != 0.0F) {
                val set = ConstraintSet()
                set.clone(container)
                set.constrainWidth(image.id, (Dimens.latestCardSize * apod.imageAspectRatio).toInt())
            }

            Glide.with(itemView)
                .load(apod.imageUrl)
                .transition(withCrossFade(factory))
                .into(image)
        } else {
                image.setBackgroundResource(android.R.color.transparent)
        }
        logo.visibility = if (apod.media_type == "video") View.VISIBLE else View.GONE

        itemView.setOnClickListener {
            clickListener(image, apod)
        }
    }
}
