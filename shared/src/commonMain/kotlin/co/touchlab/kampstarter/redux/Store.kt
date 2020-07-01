package co.touchlab.kampstarter.redux

import org.reduxkotlin.*

val loggingMiddleware = middleware<AppState> { store, next, action ->
    //log here
    next(action)
}

val store : Store<AppState>
    get() = createThreadSafeStore(
    combineReducers(appStateReducer()),
    AppState(),
    applyMiddleware(loggingMiddleware)
)

