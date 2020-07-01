package co.touchlab.kampstarter.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampstarter.android.adapter.MainAdapter
import co.touchlab.kampstarter.redux.store
import co.touchlab.kampstarter.splash.SplashActions
import co.touchlab.kampstarter.splash.SplashInteractor
import co.touchlab.kampstarter.splash.SplashState
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@InternalCoroutinesApi
class MainActivity : AppCompatActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SplashInteractor().init()
    }
}