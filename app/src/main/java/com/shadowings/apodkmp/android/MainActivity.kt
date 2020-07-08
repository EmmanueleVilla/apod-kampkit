package com.shadowings.apodkmp.android

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.shadowings.apodkmp.android.fragments.HomeHighlightFragment
import com.shadowings.apodkmp.android.fragments.SplashFragment
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
}
