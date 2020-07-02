package co.touchlab.kampstarter.redux

import android.content.Context
import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.russhwolf.settings.AndroidSettings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.core.KoinComponent
import org.koin.core.get

actual fun enhanceDependencies(dep: Dependencies) {
    val container = object : KoinComponent {
        val appContext: Context = get()
    }
    with(dep) {
        log = Kermit(LogcatLogger()).withTag("APOD")
        settings = AndroidSettings(container.appContext.getSharedPreferences("APOD_SETTINGS", Context.MODE_PRIVATE))
        sqlDriver = AndroidSqliteDriver(ApodDb.Schema, container.appContext, "APODDb")
        databaseHelper = DatabaseHelper(sqlDriver, log)
    }
}