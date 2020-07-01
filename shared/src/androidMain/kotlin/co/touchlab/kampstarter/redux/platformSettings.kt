package co.touchlab.kampstarter.redux

import android.content.Context
import android.content.SharedPreferences
import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual val platformSettings: Settings
    get() = AndroidSettings((SL[Context::class] as Context).getSharedPreferences("APOD_SETTINGS", Context.MODE_PRIVATE))

actual val platformLogger: Kermit
    get() = Kermit(LogcatLogger()).withTag("APOD")

actual val platformSqlDriver: SqlDriver
    get() = AndroidSqliteDriver(
        ApodDb.Schema,
        SL[Context::class] as Context,
        "APODDb"
    )