package com.shadowings.apodkmp.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application(), Application.ActivityLifecycleCallbacks {

    lateinit var activity: MainActivity

    override fun onCreate() {
        startKoin {
            modules(module { single<Context> { this@MainApp } })
        }

        registerActivityLifecycleCallbacks(this)

        super.onCreate()
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        this.activity = activity as MainActivity
    }

    override fun onActivityResumed(activity: Activity) {
    }
}
