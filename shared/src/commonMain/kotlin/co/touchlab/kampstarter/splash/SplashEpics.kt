package co.touchlab.kampstarter.splash
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.redux.AppState
import co.touchlab.kampstarter.redux.Epic
import co.touchlab.kampstarter.response.ApodResult
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal val splashEpics: Epic <AppState> = { store, state, action, dep ->
    when (action) {
        is SplashActions.ApodFetch.Request -> {
            handleApodRequest(store, state, action, dep)
        }

        is SplashActions.ApodFetch.FetchFromWeb -> {
            handleFetchFromWeb(store, state, action, dep)
        }
    }
}

private val handleApodRequest: Epic <AppState> = { store, state, action, dep ->
    val lastDownloadTimeMS = dep.settings.getLong("DB_TIMESTAMP_KEY", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if(expired) {
        store.dispatch(SplashActions.ApodFetch.FetchFromWeb)
    } else {
        store.dispatch(SplashActions.ApodFetch.LoadFromCache)
    }
}

private val handleFetchFromWeb: Epic <AppState> = { store, state, action, dep ->
    GlobalScope.launch {
        try {
            dep.logger.d { "Fetching Apods from network" }

            val apodResult = dep.httpClient.get<ApodResult> {
                url {
                    takeFrom("https://api.nasa.gov/")
                    encodedPath = "planetary/apod?api_key=OFxlCY0NrHskLzRpbnSjUh2xpgkVPLg3Pfq98jfQ"
                }
            }

            dep.logger.v { "Fetched ${apodResult.date} apod from network" }
            dep.databaseHelper.insertApods(listOf(apodResult))
            dep.settings.putLong("DB_TIMESTAMP_KEY", currentTimeMillis())
            store.dispatch(SplashActions.ApodFetch.Completed(apodResult))
        } catch (e: Exception) {
            dep.logger.v { e.toString() }
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
}
