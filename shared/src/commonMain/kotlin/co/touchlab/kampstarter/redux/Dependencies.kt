package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient

enum class InjectionTypes {
    SETTINGS,
    LOGGER,
    SQL_DRIVER,
    DB_HELPER,
    CONTEXT,
    HTTP_CLIENT
}

data class Dependencies(
    val settings: Settings = SL[InjectionTypes.SETTINGS],
    val logger: Kermit = SL[InjectionTypes.LOGGER],
    val sqlDriver: SqlDriver = SL[InjectionTypes.SQL_DRIVER],
    val databaseHelper: DatabaseHelper = SL[InjectionTypes.DB_HELPER],
    val httpClient: HttpClient = SL[InjectionTypes.HTTP_CLIENT]
)

private var _dep : Dependencies? = null
val dep : Dependencies
    get() {
        if(_dep == null) {
            _dep = Dependencies()
        }
        return _dep!!
    }