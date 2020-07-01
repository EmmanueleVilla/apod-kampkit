package co.touchlab.kampstarter.android

import android.app.Application
import android.content.Context
import co.touchlab.kampstarter.redux.SL

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SL.register(Context::class) { this }

    }
}