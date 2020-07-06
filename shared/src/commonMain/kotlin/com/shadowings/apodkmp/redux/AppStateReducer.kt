package com.shadowings.apodkmp.redux

import com.shadowings.apodkmp.home.homeReducer

fun rootReducer(state: AppState, action: Any) = AppState(
    homeState = homeReducer(state.homeState, action)
)
