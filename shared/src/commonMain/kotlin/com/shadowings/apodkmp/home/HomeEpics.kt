package com.shadowings.apodkmp.home
import com.russhwolf.settings.get
import com.shadowings.apodkmp.currentTimeMillis
import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.Action
import com.shadowings.apodkmp.redux.AppState
import com.shadowings.apodkmp.redux.Dependencies
import com.shadowings.apodkmp.redux.Platforms
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.reduxkotlin.Store

fun homeEpics(store: Store<AppState>, action: Action, dep: Dependencies) {
    when (action) {
        is HomeActions.ApodFetch.Request -> {
            handleApodRequest(store, action, dep)
        }

        is HomeActions.ApodFetch.FetchFromWeb -> {
            handleFetchFromWeb(store, action, dep)
        }

        is HomeActions.ApodFetch.DownloadCompleted -> {
            handleDownloadCompleted(store, action, dep)
        }

        is HomeActions.ApodFetch.LoadFromCache -> {
            handleLoadFromCache(store, action, dep)
        }
    }
}

private fun handleApodRequest(store: Store<AppState>, action: Action, dep: Dependencies) {
    val lastDownloadTimeMS = dep.storage.settings.getLong("DB_TIMESTAMP_KEY", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if (expired || dep.utils.getPlatform() == Platforms.Js) {
        store.dispatch(HomeActions.ApodFetch.FetchFromWeb)
    } else {
        store.dispatch(HomeActions.ApodFetch.LoadFromCache)
    }
}

private fun handleFetchFromWeb(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            val apodResult = dep.http.httpClient.get<Apod> {
                url {
                    takeFrom("https://young-ridge-07105.herokuapp.com/")
                    encodedPath = "apod"
                }
            }
            if (dep.utils.getPlatform() != Platforms.Js) {
                store.dispatch(
                    HomeActions.ApodFetch.DownloadCompleted(
                        apodResult
                    )
                )
            } else {
                store.dispatch(
                    HomeActions.ApodFetch.Completed(
                        apodResult
                    )
                )
            }
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
            store.dispatch(HomeActions.ApodFetch.LoadFromCache)
        }
    }
}

private fun handleDownloadCompleted(store: Store<AppState>, action: HomeActions.ApodFetch.DownloadCompleted, dep: Dependencies) {
    MainScope().launch {
        try {
            val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer(), action.payload)
            dep.storage.settings.putString(action.payload.date, json)
            dep.storage.settings.putLong("DOWNLOAD_TIMESTAMP_KEY", currentTimeMillis())
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
        } finally {
            store.dispatch(HomeActions.ApodFetch.LoadFromCache)
        }
    }
}

private fun handleLoadFromCache(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            val apodJson = dep.storage.settings[dep.utils.date(0), ""]
            if (apodJson == "") {
                store.dispatch(HomeActions.ApodFetch.Error("Nothing cached"))
            } else {
                store.dispatch(
                    HomeActions.ApodFetch.Completed(
                        Json(
                            JsonConfiguration.Stable
                        ).parse(Apod.serializer(), apodJson)
                    )
                )
            }
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
            store.dispatch(HomeActions.ApodFetch.Error(e.toString()))
        }
    }
}
