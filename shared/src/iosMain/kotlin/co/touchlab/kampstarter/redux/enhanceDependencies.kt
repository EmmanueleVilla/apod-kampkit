package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import platform.Foundation.NSUserDefaults

actual fun enhanceDependencies(dep: Dependencies) {
    with(dep) {
        log = Kermit(NSLogLogger()).withTag("APOD")
        settings = AppleSettings(NSUserDefaults(suiteName = "APOD_SETTINGS"))
        sqlDriver = NativeSqliteDriver(ApodDb.Schema, "apoddb")
        databaseHelper = DatabaseHelper(sqlDriver, log)
    }
}