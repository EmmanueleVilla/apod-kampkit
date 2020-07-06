package com.shadowings.apodkmp.splash

import com.shadowings.apodkmp.redux.AppState
import com.shadowings.apodkmp.redux.store
import org.reduxkotlin.StoreSubscription

class SplashInteractor {

    var subscription: StoreSubscription? = null

    fun init() {
        store.dispatch(SplashActions.ApodFetch.Request)
    }

    fun subscribe(callback: (state: AppState) -> Unit) {
        subscription = store.subscribe {
            callback.invoke(store.state)
        }
    }

    fun unsubscribe() {
        if (subscription != null) {
            subscription!!.invoke()
        }
    }
}
