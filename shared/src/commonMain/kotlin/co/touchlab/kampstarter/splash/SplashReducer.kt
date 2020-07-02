package co.touchlab.kampstarter.splash

import org.reduxkotlin.Reducer

val splashReducer: Reducer<SplashState> =  { state, action ->
    when (action) {
        is SplashActions.ApodFetch.Completed -> state.copy(apod = action.payload)
        else -> state
    }
}