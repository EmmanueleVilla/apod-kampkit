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
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.reduxkotlin.Store

fun homeEpics(store: Store<AppState>, action: Action, dep: Dependencies) {
    when (action) {
        is HomeActions.LatestFetch.Request -> {
            handleLatestRequest(store, action, dep)
        }

        is HomeActions.LatestFetch.FetchFromWeb -> {
            handleLatestFetchFromWeb(store, action, dep)
        }

        is HomeActions.LatestFetch.DownloadCompleted -> {
            handleDownloadCompleted(store, action, dep)
        }

        is HomeActions.LatestFetch.LoadFromCache -> {
            handleLoadLatestFromCache(store, action, dep)
        }
    }
}

private fun handleLatestRequest(store: Store<AppState>, action: Action, dep: Dependencies) {
    val lastDownloadTimeMS = dep.storage.settings.getLong("LATEST_TIMESTAMP", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if (expired || dep.utils.getPlatform() == Platforms.Js) {
        store.dispatch(HomeActions.LatestFetch.FetchFromWeb)
    } else {
        store.dispatch(HomeActions.LatestFetch.LoadFromCache)
    }
}

private fun handleLatestFetchFromWeb(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            val apodResult = dep.http.httpClient.get<Array<Apod>> {
                url {
                    takeFrom("https://young-ridge-07105.herokuapp.com/")
                    encodedPath = "latest"
                }
            }
            if (dep.utils.getPlatform() != Platforms.Js) {
                store.dispatch(
                    HomeActions.LatestFetch.DownloadCompleted(
                        apodResult.toList()
                    )
                )
            } else {
                store.dispatch(
                    HomeActions.LatestFetch.Completed(
                        apodResult.toList()
                    )
                )
            }
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
            store.dispatch(HomeActions.LatestFetch.LoadFromCache)
        }
    }
}

private fun handleDownloadCompleted(store: Store<AppState>, action: HomeActions.LatestFetch.DownloadCompleted, dep: Dependencies) {
    MainScope().launch {
        try {
            val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, action.payload)
            dep.storage.settings.putString("LATEST", json)
            dep.storage.settings.putLong("DOWNLOAD_TIMESTAMP_KEY", currentTimeMillis())
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
        } finally {
            store.dispatch(HomeActions.LatestFetch.LoadFromCache)
        }
    }
}

private fun handleLoadLatestFromCache(store: Store<AppState>, action: Action, dep: Dependencies) {
    MainScope().launch {
        try {
            val apodJson = dep.storage.settings["LATEST", ""]
            if (apodJson == "") {
                store.dispatch(HomeActions.LatestFetch.Error("Nothing cached"))
            } else {
                store.dispatch(
                    HomeActions.LatestFetch.Completed(
                        Json(
                            JsonConfiguration.Stable
                        ).parse(Apod.serializer().list, apodJson)
                    )
                )
            }
        } catch (e: Exception) {
            dep.utils.log.v { e.toString() }
            store.dispatch(HomeActions.LatestFetch.Error(e.toString()))
        }
    }
}
