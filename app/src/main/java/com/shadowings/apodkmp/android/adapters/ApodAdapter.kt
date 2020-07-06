package com.shadowings.apodkmp.android.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        if (!apod.url.contains("youtube")) {
            Glide.with(itemView).load(apod.url).into((itemView as ApodView).image)
        }
    }
}

class ApodView(context: Context) : LinearLayout(context) {
    var image: AppCompatImageView = AppCompatImageView(context)
    init {
        this.id = View.generateViewId()
        this.layoutParams = LayoutParams(300, 300)

        image.id = View.generateViewId()
        image.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        image.setPadding(24, 24, 24, 24)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        addView(image)
    }
}
