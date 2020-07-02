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
import org.reduxkotlin.*

val loggingMiddleware = middleware<AppState> { store, next, action ->
    dep.log.log(Severity.Assert, throwable = null, message = { "dispatching action " + action::class.qualifiedName })
    next(action)
}

val epicMiddleware = middleware<AppState> { store, next, action ->
    next(action)
    appStateEpic.forEach { it.invoke(store, store.state, action as Action, dep) }
}

private var _store : Store<AppState>? = null
val store : Store<AppState>
    get() {
        if(_store == null) {
            enhanceDependencies(dep)
            _store = createThreadSafeStore(
                combineReducers(appStateReducer),
                AppState(),
                applyMiddleware(loggingMiddleware, epicMiddleware))
        }
        return _store!!
    }

class Dependencies {
    lateinit var settings: Settings
    lateinit var log: Kermit
    lateinit var sqlDriver: SqlDriver
    lateinit var databaseHelper: DatabaseHelper
    var httpClient: HttpClient = HttpClient {
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
}

private var _dep : Dependencies? = null
private val dep : Dependencies
    get() {
        if(_dep == null) {
            _dep = Dependencies()
        }
        return _dep!!
    }

expect fun enhanceDependencies(dep: Dependencies)