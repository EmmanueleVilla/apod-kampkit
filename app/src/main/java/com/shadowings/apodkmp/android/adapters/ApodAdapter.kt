package com.shadowings.apodkmp.android.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.shadowings.apodkmp.android.utils.Dimens
import com.shadowings.apodkmp.model.Apod

class ApodAdapter : RecyclerView.Adapter<ApodViewHolder>() {

    var apods: List<Apod> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        return ApodViewHolder(ApodView(parent.context))
    }

    override fun getItemCount(): Int {
        return apods.size
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        holder.setData(apods[position])
    }
}

class ApodViewHolder(itemView: ApodView) : RecyclerView.ViewHolder(itemView) {
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
    }
}

class ApodView(context: Context) : LinearLayout(context) {
    var image: AppCompatImageView = AppCompatImageView(context)
    init {
        this.id = View.generateViewId()
        this.layoutParams = LayoutParams(Dimens.latestCardSize, Dimens.latestCardSize)

        image.id = View.generateViewId()
        val layoutParams = MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        layoutParams.setMargins(Dimens.margin, Dimens.margin, Dimens.margin, Dimens.margin)
        image.layoutParams = layoutParams
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        image.setBackgroundColor(Color.GRAY)
        addView(image)
    }
}
