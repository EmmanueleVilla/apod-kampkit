package co.touchlab.kampstarter.splash
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.redux.Action
import co.touchlab.kampstarter.redux.AppState
import co.touchlab.kampstarter.redux.Dependencies
import co.touchlab.kampstarter.response.ApodResult
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.reduxkotlin.Store

fun splashEpics(store: Store<AppState>, action: Action, dep: Dependencies) {
    dep.log.v { "splashEpics: Handling action " + action::class.qualifiedName }
    when (action) {
        is SplashActions.ApodFetch.Request -> {
            handleApodRequest(store, action, dep)
        }

        is SplashActions.ApodFetch.FetchFromWeb -> {
            handleFetchFromWeb(store, action, dep)
        }

        is SplashActions.ApodFetch.DownloadCompleted -> {
            handleDownloadCompleted(store, action, dep)
        }

        is SplashActions.ApodFetch.LoadFromCache -> {
            handleLoadFromCache(store, action, dep)
        }
    }

}

private fun handleApodRequest(store: Store<AppState>, action: Action, dep: Dependencies) {
    dep.log.v { "splashEpics: Handling apod request" }
    val lastDownloadTimeMS = dep.settings.getLong("DB_TIMESTAMP_KEY", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if(expired) {
        store.dispatch(SplashActions.ApodFetch.FetchFromWeb)
    } else {
        store.dispatch(SplashActions.ApodFetch.LoadFromCache)
    }

}

private fun handleFetchFromWeb(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            dep.log.d { "Fetching Apods from network" }

            val apodResult = dep.httpClient.get<ApodResult> {
                url {
                    takeFrom("https://api.nasa.gov/")
                    encodedPath = "planetary/apod?api_key=OFxlCY0NrHskLzRpbnSjUh2xpgkVPLg3Pfq98jfQ"
                }
            }
            store.dispatch(SplashActions.ApodFetch.DownloadCompleted(apodResult))
        } catch (e: Exception) {
            dep.log.v { e.toString() }
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
}

private fun handleDownloadCompleted(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            dep.databaseHelper.insertApods(listOf((action as SplashActions.ApodFetch.DownloadCompleted).payload))
            dep.settings.putLong("DB_TIMESTAMP_KEY", currentTimeMillis())

        } catch (e: Exception) {
            dep.log.v { e.toString() }
        } finally {
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
}

private fun handleLoadFromCache(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            dep.databaseHelper.selectAllItems(1, 0).collect { value ->
                if (value.isNotEmpty()) {
                    store.dispatch(SplashActions.ApodFetch.Completed(value.first()))
                }
            }
        } catch (e: Exception) {
            dep.log.v { e.toString() }
            store.dispatch(SplashActions.ApodFetch.Error(e.toString()))
        }
    }
}
