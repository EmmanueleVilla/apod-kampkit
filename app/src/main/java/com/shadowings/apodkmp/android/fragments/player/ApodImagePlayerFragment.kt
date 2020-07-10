package com.shadowings.apodkmp.android.fragments.player

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.jsibbold.zoomage.AutoResetMode.NEVER
import com.jsibbold.zoomage.ZoomageView
import com.shadowings.apodkmp.android.MainActivity
import com.shadowings.apodkmp.model.Apod

class ApodImagePlayerFragment(private val apod: Apod) : Fragment() {

    lateinit var image: ZoomageView
    lateinit var loading: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val cont = LinearLayout(context)
        cont.setBackgroundColor(Color.BLACK)
        cont.gravity = CENTER
        image = buildImage(context!!)
        loading = ProgressBar(context!!)
        loading.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        loading.isIndeterminate = true
        image.visibility = GONE
        cont.addView(loading)
        cont.addView(image)
        cont.isClickable = true
        return cont
    }

    private fun buildImage(context: Context): ZoomageView {
        val view = ZoomageView(context)
        view.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        with(view) {
            restrictBounds = true
            animateOnReset = true
            autoResetMode = NEVER
            autoCenter = true
            isZoomable = true
            isTranslatable = true
            setScaleRange(1.0f, 10.0f)
        }
        return view
    }

    override fun onResume() {
        super.onResume()

        (context as MainActivity).window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        (context as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

        if (apod.media_type == "image") {
            Glide.with(context!!)
                .load(apod.imageUrlHD)
                .into(object : CustomViewTarget<ImageView, Drawable>(image) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        loading.visibility = GONE
                        image.visibility = VISIBLE
                        image.setImageDrawable(resource)
                    }
                })
        }
    }

    override fun onPause() {
        super.onPause()
        (context as MainActivity).window.clearFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        (context as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
