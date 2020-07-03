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

actual fun createDependencies(): Dependencies {
    val container = object : KoinComponent {
        val appContext: Context = get()
    }
    val sqlDriver = AndroidSqliteDriver(ApodDb.Schema, container.appContext, "APODDb")
    val log = Kermit(LogcatLogger()).withTag("APOD")
    return Dependencies(
        log = log,
        settings = AndroidSettings(container.appContext.getSharedPreferences("APOD_SETTINGS", Context.MODE_PRIVATE)),
        sqlDriver = sqlDriver,
        databaseHelper = DatabaseHelper(sqlDriver, log)
    )
}

actual fun getActionName(action: Action): String {
    if(action::class.qualifiedName != null) {
        return action::class.qualifiedName!!
    }
    return action.toString()
}