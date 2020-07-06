package com.shadowings.apodkmp.home

fun homeReducer(state: HomeState, action: Any): HomeState {
    return when (action) {
        is HomeActions.LatestFetch.Completed -> state.copy(latest = action.payload)
        else -> state
    }
}
