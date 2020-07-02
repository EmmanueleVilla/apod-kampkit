package co.touchlab.kampstarter.android

import android.app.Application
import android.content.Context
import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kampstarter.redux.InjectionTypes
import co.touchlab.kampstarter.redux.SL
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SL.set<Context>(InjectionTypes.CONTEXT) { this }
        SL.set<Kermit>(InjectionTypes.LOGGER) { Kermit(LogcatLogger()).withTag("APOD") }
        SL.set<Settings>(InjectionTypes.SETTINGS) { AndroidSettings(this.getSharedPreferences("APOD_SETTINGS", Context.MODE_PRIVATE)) }
        SL.set<SqlDriver>(InjectionTypes.SQL_DRIVER) { AndroidSqliteDriver(ApodDb.Schema, this, "APODDb") }
        SL.set<DatabaseHelper>(InjectionTypes.DB_HELPER) { DatabaseHelper()}
    }
}