package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.ktor.ApodApiImpl
import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.splash.SplashState
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers

data class AppState(val dependencies: Dependencies = Dependencies(), val splashState: SplashState = SplashState())

expect val platformSettings : Settings
expect val platformLogger: Kermit
expect val platformSqlDriver: SqlDriver

data class Dependencies(
    val settings: Settings = platformSettings,
    val logger: Kermit = platformLogger,
    val sqlDriver: SqlDriver = platformSqlDriver,
    val databaseHelper: DatabaseHelper = DatabaseHelper(platformSqlDriver, logger, Dispatchers.Default),
    val apodApi: KtorApi = ApodApiImpl(logger)
)