package co.touchlab.kampstarter.splash
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.redux.AppState
import co.touchlab.kampstarter.redux.Epic
import co.touchlab.kampstarter.response.ApodResult
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.native.concurrent.SharedImmutable

internal val splashEpics: Epic <AppState> = { store, state, action, dep ->
    /*

    when (action) {
        is SplashActions.ApodFetch.Request -> {
            handleApodRequest(store, state, action, dep)
        }

        is SplashActions.ApodFetch.FetchFromWeb -> {
            handleFetchFromWeb(store, state, action, dep)
        }

        is SplashActions.ApodFetch.DownloadCompleted -> {
            handleDownloadCompleted(store, state, action, dep)
        }

        is SplashActions.ApodFetch.LoadFromCache -> {
            handleLoadFromCache(store, state, action, dep)
        }
    }
     */
}

private val handleApodRequest: Epic <AppState> = { store, state, action, dep ->
    /*
    val lastDownloadTimeMS = dep.settings.getLong("DB_TIMESTAMP_KEY", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if(expired) {
        store.dispatch(SplashActions.ApodFetch.FetchFromWeb)
    } else {
        store.dispatch(SplashActions.ApodFetch.LoadFromCache)
    }

     */
}

private val handleFetchFromWeb: Epic <AppState> = { store, state, action, dep ->
    /*
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

     */
}

private val handleDownloadCompleted: Epic <AppState> = { store, state, action, dep ->
    /*
    GlobalScope.launch {
        try {
            dep.databaseHelper.insertApods(listOf((action as SplashActions.ApodFetch.DownloadCompleted).payload))
            dep.settings.putLong("DB_TIMESTAMP_KEY", currentTimeMillis())

        } catch (e: Exception) {
            dep.log.v { e.toString() }
        } finally {
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
     */
}

private val handleLoadFromCache: Epic <AppState> = { store, state, action, dep ->
    /*
    GlobalScope.launch {
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
     */
}
