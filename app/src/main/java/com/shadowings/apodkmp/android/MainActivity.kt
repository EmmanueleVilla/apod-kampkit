package com.shadowings.apodkmp.android

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import com.shadowings.apodkmp.android.fragments.HomeHighlightFragment
import com.shadowings.apodkmp.android.fragments.ImageDetailFrament
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
        val detail = ImageDetailFrament(apod, displayMetrics.heightPixels / 2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detail.enterTransition = Fade()
        }
        supportFragmentManager
            .beginTransaction()
            .add(frameLayout.id, detail)
            .addToBackStack(apod.date)
            .commit()
    }

    fun openDetailFromFragment(apod: Apod, from: Fragment, height: Int) {

        val detail = ImageDetailFrament(apod, height)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detail.enterTransition = Fade()
            from.exitTransition = Fade()
        }
        supportFragmentManager
            .beginTransaction()
            .add(frameLayout.id, detail)
            .addToBackStack(apod.date)
            .commit()
    }
}
