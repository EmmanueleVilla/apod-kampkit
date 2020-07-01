package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import platform.Foundation.NSUserDefaults

actual val platformSettings: Settings
    get() = AppleSettings(NSUserDefaults(suiteName = "APOD_SETTINGS"))

actual val platformLogger: Kermit
    get() = Kermit(NSLogLogger()).withTag("APOD")

actual val platformSqlDriver: SqlDriver
    get() = NativeSqliteDriver(ApodDb.Schema, "apoddb")