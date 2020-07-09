package com.shadowings.apodkmp.android.adapters

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampstarter.android.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.shadowings.apodkmp.android.utils.dsl.ConstraintPositions
import com.shadowings.apodkmp.android.utils.dsl.Dimens
import com.shadowings.apodkmp.android.utils.dsl.Dimens.logo
import com.shadowings.apodkmp.android.utils.dsl.constraintLayout
import com.shadowings.apodkmp.model.Apod

class ApodAdapter(var clickListener: (image: AppCompatImageView, apod: Apod) -> Unit) : RecyclerView.Adapter<ApodViewHolder>() {

    var apods: List<Apod> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        var image: AppCompatImageView? = null
        var logo: AppCompatImageView? = null
        val view = constraintLayout(height = Dimens.latestCardSize, width = Dimens.latestCardSize) {
            image = image(position = ConstraintPositions.Center)
            logo = image(drawable = R.drawable.youtube_logo, width = Dimens.logo / 2, height = Dimens.logo / 2, position = ConstraintPositions.BottomStart)
        }
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
    itemView: View,
    private val image: AppCompatImageView,
    private val logo: AppCompatImageView,
    val clickListener: (image: AppCompatImageView, apod: Apod) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {
    fun setData(apod: Apod) {
        if (apod.imageUrl != "") {
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
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
