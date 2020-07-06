package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.redux.AppState
import com.shadowings.apodkmp.redux.store
import org.reduxkotlin.StoreSubscription

class HomeInteractor {

    var subscription: StoreSubscription? = null

    fun init() {
        store.dispatch(HomeActions.LatestFetch.Request)
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
