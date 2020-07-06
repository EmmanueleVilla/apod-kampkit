package com.shadowings.apodkmp.home

fun homeReducer(state: HomeState, action: Any): HomeState {
    return when (action) {
        is HomeActions.ApodFetch.Completed -> state.copy(apod = action.payload)
        else -> state
    }
}
