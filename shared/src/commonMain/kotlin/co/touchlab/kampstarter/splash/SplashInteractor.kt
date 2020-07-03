package co.touchlab.kampstarter.splash

import co.touchlab.kampstarter.redux.AppState
import co.touchlab.kampstarter.redux.store
import org.reduxkotlin.StoreSubscription

class SplashInteractor {

    var unsubscribe: StoreSubscription? = null

    fun init() {
        store.dispatch(SplashActions.ApodFetch.Request)
    }

    fun subscribe(callback: (state: AppState) -> Unit) {
        unsubscribe = store.subscribe {
           callback.invoke(store.state)
        }
    }

    fun unsubscribe() {
        if(unsubscribe != null) {
            unsubscribe!!.invoke()
        }
    }
}