package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.splashReducer
import org.reduxkotlin.Reducer

val appStateReducer : Reducer<AppState>  =  { state, action ->
    state.copy(splashState = splashReducer(state.splashState, action))
}