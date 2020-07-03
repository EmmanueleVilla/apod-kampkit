package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.splashReducer
import org.reduxkotlin.Reducer
import kotlin.native.concurrent.ThreadLocal

fun rootReducer(state: AppState, action: Any) = AppState(
    splashState = splashReducer(state.splashState, action)
)
