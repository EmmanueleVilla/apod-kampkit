package com.shadowings.apodkmp.redux

import com.shadowings.apodkmp.splash.splashReducer

fun rootReducer(state: AppState, action: Any) = AppState(
    splashState = splashReducer(state.splashState, action)
)
