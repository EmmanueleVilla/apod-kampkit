package co.touchlab.kampstarter.android

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import co.touchlab.kampstarter.redux.store
import co.touchlab.kampstarter.splash.SplashInteractor
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    lateinit var container : ConstraintLayout
    lateinit var image : ImageView
    lateinit var description : TextView

    val splashInteractor : SplashInteractor = SplashInteractor()

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
                if(it.splashState.apod != null) {
                    description.text = it.splashState.apod!!.explanation
                    Glide.with(this).load(it.splashState.apod!!.url).into(image)
                }
            }
        }

        splashInteractor.init()
    }

    override fun onDestroy() {
        splashInteractor.unsubscribe()
        super.onDestroy()
    }
}