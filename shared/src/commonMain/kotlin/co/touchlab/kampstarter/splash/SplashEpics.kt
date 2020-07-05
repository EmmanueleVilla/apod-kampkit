package co.touchlab.kampstarter.splash
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.model.Apod
import co.touchlab.kampstarter.redux.*
import com.russhwolf.settings.get
import io.ktor.client.request.get
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Store
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

fun splashEpics(store: Store<AppState>, action: Action, dep: Dependencies) {
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
    val lastDownloadTimeMS = dep.storage.settings.getLong("DB_TIMESTAMP_KEY", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if(expired || dep.utils.getPlatform() == Platforms.Js) {
        store.dispatch(SplashActions.ApodFetch.FetchFromWeb)
    } else {
        store.dispatch(SplashActions.ApodFetch.LoadFromCache)
    }

}

private fun handleFetchFromWeb(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            val apodResult = dep.http.httpClient.get<Apod> {
                url {
                    takeFrom("https://api.nasa.gov/")
                    encodedPath = "planetary/apod?api_key=OFxlCY0NrHskLzRpbnSjUh2xpgkVPLg3Pfq98jfQ"
                }
            }
            if(dep.utils.getPlatform() != Platforms.Js) {
                store.dispatch(SplashActions.ApodFetch.DownloadCompleted(apodResult))
            } else {
                store.dispatch(SplashActions.ApodFetch.Completed(apodResult))
            }
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
}

private fun handleDownloadCompleted(store: Store<AppState>, action: SplashActions.ApodFetch.DownloadCompleted, dep: Dependencies) {
    MainScope().launch {
        try {
            val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer(), action.payload)
            dep.storage.settings.putString(action.payload.date, json)
            dep.storage.settings.putLong("DOWNLOAD_TIMESTAMP_KEY", currentTimeMillis())
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
        } finally {
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
}

private fun handleLoadFromCache(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            val apodJson = dep.storage.settings[dep.utils.today(), ""]
            if(apodJson == "") {
                store.dispatch(SplashActions.ApodFetch.Error("Nothing cached"))
            } else {
                store.dispatch(SplashActions.ApodFetch.Completed(Json(JsonConfiguration.Stable).parse(Apod.serializer(), apodJson)))
            }
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
            store.dispatch(SplashActions.ApodFetch.Error(e.toString()))
        }
    }
}
