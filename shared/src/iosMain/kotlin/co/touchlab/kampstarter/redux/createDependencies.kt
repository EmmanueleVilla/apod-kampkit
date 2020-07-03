package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import platform.Foundation.NSUserDefaults

actual fun createDependencies(): Dependencies {
    val sqlDriver = NativeSqliteDriver(ApodDb.Schema, "apoddb")
    val log = Kermit(NSLogLogger()).withTag("APOD")
    return Dependencies(
        log = log,
        settings = AppleSettings(NSUserDefaults(suiteName = "APOD_SETTINGS")),
        sqlDriver = sqlDriver,
        databaseHelper = DatabaseHelper(sqlDriver, log)
    )
}