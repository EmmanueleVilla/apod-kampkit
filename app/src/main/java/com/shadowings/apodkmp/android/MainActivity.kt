package com.shadowings.apodkmp.android

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.Fade
import androidx.transition.TransitionSet
import com.shadowings.apodkmp.android.fragments.ApodDetailFragment
import com.shadowings.apodkmp.android.fragments.HomeHighlightFragment
import com.shadowings.apodkmp.android.fragments.SplashFragment
import com.shadowings.apodkmp.model.Apod
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            return
        }
        frameLayout = FrameLayout(this)
        frameLayout.id = View.generateViewId()
        setContentView(frameLayout)
        supportFragmentManager
            .beginTransaction()
            .replace(frameLayout.id, SplashFragment())
            .commit()
    }

    fun openHome() {
        supportFragmentManager.beginTransaction()
            .replace(frameLayout.id, HomeHighlightFragment())
            .commit()
    }

    fun openDetail(apod: Apod) {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val detail = ApodDetailFragment(apod, displayMetrics.heightPixels / 2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detail.enterTransition = Fade()
        }
        supportFragmentManager
            .beginTransaction()
            .add(frameLayout.id, detail)
            .addToBackStack(apod.date)
            .commit()
    }

    fun openDetailWithHeight(apod: Apod, height: Int) {
        val detail = ApodDetailFragment(apod, height)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detail.enterTransition = Fade()
        }
        supportFragmentManager
            .beginTransaction()
            .add(frameLayout.id, detail)
            .addToBackStack(apod.date)
            .commit()
    }

    fun openDetailWithTransaction(apod: Apod, from: Fragment, view: ImageView, height: Int) {
        val detail = ApodDetailFragment(apod, height)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detail.sharedElementEnterTransition = DetailsTransition()
            detail.sharedElementReturnTransition = DetailsTransition()
            detail.enterTransition = Fade()
            from.exitTransition = Fade()
        }
        supportFragmentManager
            .beginTransaction()
            .detach(from)
            .add(frameLayout.id, detail)
            .addSharedElement(view, apod.date)
            .addToBackStack(apod.date)
            .commit()
    }

    class DetailsTransition : TransitionSet() {
        init {
            ordering = ORDERING_TOGETHER
            addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
        }
    }
}
