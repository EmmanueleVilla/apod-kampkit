package co.touchlab.kampstarter.splash

import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.redux.Action

sealed class SplashActions : Action() {
    sealed class ApodFetch : Action() {
        object Request : ApodFetch()
        object LoadFromCache : ApodFetch()
        object FetchFromWeb : ApodFetch()
        data class Completed(val model: Apods) : ApodFetch()
        data class Error(val message: String) : ApodFetch()
    }
}