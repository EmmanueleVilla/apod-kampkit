package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.splashReducer
import org.reduxkotlin.Reducer

fun appStateReducer(state: AppState = AppState(), action: Any = false ) : Reducer<AppState>  =  { state, action ->
    state.copy(splashState = splashReducer(state.splashState, action))
}