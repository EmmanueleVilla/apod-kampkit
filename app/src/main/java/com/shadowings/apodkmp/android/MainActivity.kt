package com.shadowings.apodkmp.android

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.shadowings.apodkmp.android.fragments.SplashFragment
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    // private lateinit var image : ImageView
    // private lateinit var description : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            return
        }
        val frameLayout: FrameLayout = FrameLayout(this)
        frameLayout.id = View.generateViewId()
        setContentView(frameLayout)
        supportFragmentManager
            .beginTransaction()
            .replace(frameLayout.id, SplashFragment())
            .commit()
        /*


        image = ImageView(this)
        image.id = View.generateViewId()

        description = TextView(this)
        description.id = View.generateViewId()

        val set = ConstraintSet ()
        set.connect(image.id, ConstraintSet.START, container.id, ConstraintSet.START)
        set.connect(image.id, ConstraintSet.END, container.id, ConstraintSet.END)
        set.connect(image.id, ConstraintSet.TOP, container.id, ConstraintSet.TOP)
        set.constrainHeight(image.id, 200)

        set.connect(description.id, ConstraintSet.START, container.id, ConstraintSet.START, 12)
        set.connect(description.id, ConstraintSet.END, container.id, ConstraintSet.END, 12)
        set.connect(description.id, ConstraintSet.TOP, image.id, ConstraintSet.BOTTOM, 12)
        set.connect(description.id, ConstraintSet.BOTTOM, container.id, ConstraintSet.BOTTOM, 12)

        container.addView(image)
        container.addView(description)
        container.setConstraintSet(set)
        container.setBackgroundColor(Color.CYAN)

        setContentView(container)


         */
    }
}
