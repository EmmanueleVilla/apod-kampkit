package co.touchlab.kampstarter.splash

fun splashReducer(state: SplashState, action: Any): SplashState {
    return when (action) {
        is SplashActions.ApodFetch.Completed -> state.copy(apod = action.payload)
        else -> state
    }
}
