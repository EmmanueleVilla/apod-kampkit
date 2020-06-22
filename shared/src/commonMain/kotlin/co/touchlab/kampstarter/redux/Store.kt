package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.SplashState
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.combineReducers
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.middleware

val loggingMiddleware = middleware<AppState> { store, next, action ->
    //log here
    next(action)
}

val store = createThreadSafeStore(
    combineReducers(appStateReducer()),
    AppState(SplashState()),
    applyMiddleware(loggingMiddleware)
)
