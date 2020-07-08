package com.shadowings.apodkmp.android.adapters

import android.content.Context
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
import com.shadowings.apodkmp.android.utils.dsl.Dimens
import com.shadowings.apodkmp.model.Apod

class ApodAdapter(var clickListener: (image: AppCompatImageView, apod: Apod) -> Unit) : RecyclerView.Adapter<ApodViewHolder>() {

    var apods: List<Apod> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        return ApodViewHolder(ApodView(parent.context), clickListener)
    }

    override fun getItemCount(): Int {
        return apods.size
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        holder.setData(apods[position])
    }
}

class ApodViewHolder(itemView: ApodView, var clickListener: (image: AppCompatImageView, apod: Apod) -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun setData(apod: Apod) {
        if (apod.imageUrl != "") {
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            Glide.with(itemView)
                .load(apod.imageUrl)
                .transition(withCrossFade(factory))
                .into((itemView as ApodView).image)
        } else {
            (itemView as ApodView).image.setBackgroundResource(android.R.color.transparent)
        }
        itemView.logo.visibility = if (apod.media_type == "video") View.VISIBLE else View.GONE

        itemView.setOnClickListener {
            clickListener(itemView.image, apod)
        }
    }
}

class ApodView(context: Context) : ConstraintLayout(context) {
    val image: AppCompatImageView = AppCompatImageView(context)
    val logo: AppCompatImageView = AppCompatImageView(context)

    init {
        this.id = View.generateViewId()
        this.layoutParams = LayoutParams(Dimens.latestCardSize, Dimens.latestCardSize)

        image.id = View.generateViewId()
        image.scaleType = ImageView.ScaleType.CENTER_INSIDE
        image.setBackgroundColor(Color.BLACK)
        addView(image)

        logo.id = View.generateViewId()
        logo.scaleType = ImageView.ScaleType.CENTER_INSIDE
        logo.setBackgroundResource(R.drawable.youtube_logo)
        logo.visibility = View.GONE
        addView(logo)

        val set = ConstraintSet()
        set.connect(image.id, ConstraintSet.START, this.id, ConstraintSet.START, Dimens.margin)
        set.connect(image.id, ConstraintSet.END, this.id, ConstraintSet.END, Dimens.margin)
        set.connect(image.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, Dimens.margin)
        set.connect(image.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM, Dimens.margin)

        set.connect(logo.id, ConstraintSet.BOTTOM, image.id, ConstraintSet.BOTTOM, Dimens.margin)
        set.connect(logo.id, ConstraintSet.START, image.id, ConstraintSet.START, Dimens.margin)
        set.constrainHeight(logo.id, 125)
        set.constrainWidth(logo.id, 125)

        set.applyTo(this)
    }
}
