package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Severity
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.reduxkotlin.*
import kotlin.native.concurrent.ThreadLocal

val loggingMiddleware = middleware<AppState> { _, next, action ->
    dep.log.log(Severity.Assert, throwable = null, message = { "dispatching action " + action::class.qualifiedName })
    next(action)
}

val epicMiddleware = middleware<AppState> { store, next, action ->
    next(action)
    GlobalScope.launch {
        appStateEpic.forEach { it(store, store.state, action as Action, dep) }
    }
}

val store : Store<AppState> = createThreadSafeStore(
    ::rootReducer,
    AppState(),
    applyMiddleware(loggingMiddleware/*, epicMiddleware*/))

data class Dependencies(
    val settings: Settings,
    val log: Kermit,
    val sqlDriver: SqlDriver,
    val databaseHelper: DatabaseHelper,
    val httpClient: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    val logger: Kermit = dep.log
                    logger.v("Network") { message }
                }
            }
            level = LogLevel.INFO
        }
    }
)

private val dep : Dependencies = createDependencies()

expect fun createDependencies() : Dependencies





