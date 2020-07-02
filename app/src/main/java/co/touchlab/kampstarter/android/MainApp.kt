package co.touchlab.kampstarter.android

import android.app.Application
import android.content.Context
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        startKoin {
            modules(module { single<Context> { this@MainApp } })
        }
        super.onCreate()
    }
}