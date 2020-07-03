package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.splashReducer

fun rootReducer(state: AppState, action: Any) = AppState(
    splashState = splashReducer(state.splashState, action)
)
