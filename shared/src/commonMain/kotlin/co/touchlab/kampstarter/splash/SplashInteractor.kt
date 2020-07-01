package co.touchlab.kampstarter.splash

import co.touchlab.kampstarter.redux.store

class SplashInteractor {
    fun init() {
        store.dispatch(SplashActions.ApodFetch.Request)
    }
}