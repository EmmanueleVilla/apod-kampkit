package co.touchlab.kampstarter.redux

import co.touchlab.kermit.Kermit
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver

actual val platformSettings: Settings
    get() = SL[InjectionTypes.SETTINGS]

actual val platformLogger: Kermit
    get() = SL[InjectionTypes.LOGGER]

actual val platformSqlDriver: SqlDriver
    get() = SL[InjectionTypes.SQL_DRIVER]