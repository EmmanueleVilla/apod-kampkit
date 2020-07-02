package co.touchlab.kampstarter.redux

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Severity
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.reduxkotlin.*

val loggingMiddleware = middleware<AppState> { store, next, action ->
    val logger : Kermit = SL[InjectionTypes.LOGGER]
    logger.log(Severity.Assert, throwable = null, message = { "dispatching action " + action::class.qualifiedName })
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
            initSL();
            _store = createThreadSafeStore(
                combineReducers(appStateReducer),
                AppState(),
                applyMiddleware(loggingMiddleware, epicMiddleware))
        }
        return _store!!
    }

private fun initSL() {
    SL.setSingle<HttpClient>(InjectionTypes.HTTP_CLIENT) { HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    val logger : Kermit = SL[InjectionTypes.LOGGER]
                    logger.v("Network") { message }
                }
            }
            level = LogLevel.INFO
        }
    } }
}
