package co.touchlab.kampstarter.android

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.splash.SplashInteractor
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent
import kotlin.with as with

class MainActivity : AppCompatActivity(), KoinComponent {
    private lateinit var container : ConstraintLayout
    private lateinit var image : ImageView
    private lateinit var description : TextView

    private val splashInteractor : SplashInteractor = SplashInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container = ConstraintLayout(this)
        container.id = View.generateViewId()

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

        splashInteractor.subscribe {
            runOnUiThread {
                val apod = it.splashState.apod
                description.text = apod.explanation
                Glide.with(this).load(apod.url).into(image)
            }
        }

        splashInteractor.init()
    }

    override fun onDestroy() {
        splashInteractor.unsubscribe()
        super.onDestroy()
    }
}