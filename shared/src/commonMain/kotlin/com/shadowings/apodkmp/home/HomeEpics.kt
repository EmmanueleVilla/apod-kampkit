package com.shadowings.apodkmp.home
import com.russhwolf.settings.get
import com.shadowings.apodkmp.currentTimeMillis
import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.Action
import com.shadowings.apodkmp.redux.Dependencies
import com.shadowings.apodkmp.redux.Platforms
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

suspend fun homeEpics(action: Action, dep: Dependencies): List<Action> {
    when (action) {
        is HomeActions.LatestFetch.Request -> {
            return handleLatestRequest(action, dep)
        }

        is HomeActions.LatestFetch.FetchFromWeb -> {
            return handleLatestFetchFromWeb(action, dep)
        }

        is HomeActions.LatestFetch.DownloadCompleted -> {
            return handleDownloadCompleted(action, dep)
        }

        is HomeActions.LatestFetch.LoadFromCache -> {
            return handleLoadLatestFromCache(action, dep)
        }

        else -> return listOf()
    }
}

suspend fun handleLatestRequest(action: Action, dep: Dependencies): List<Action> {
    val lastDownloadTimeMS = dep.storage.settings.getLong("LATEST_TIMESTAMP", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if (expired || dep.utils.getPlatform() == Platforms.Js) {
        return listOf(HomeActions.LatestFetch.FetchFromWeb)
    } else {
        return listOf(HomeActions.LatestFetch.LoadFromCache)
    }
}

suspend fun handleLatestFetchFromWeb(action: Action, dep: Dependencies): List<Action> {
    try {
        val apodResult = dep.http.httpClient.get<Array<Apod>> {
            url {
                takeFrom("https://young-ridge-07105.herokuapp.com/")
                encodedPath = "latest"
            }
        }
        return if (dep.utils.getPlatform() != Platforms.Js) {
            listOf(
                HomeActions.LatestFetch.DownloadCompleted(
                    apodResult.toList()
                )
            )
        } else {
            listOf(
                HomeActions.LatestFetch.Completed(
                    apodResult.toList()
                )
            )
        }
    } catch (e: Exception) {
        dep.utils.log.v { e.toString() }
        return listOf(HomeActions.LatestFetch.LoadFromCache)
    }
}

suspend fun handleDownloadCompleted(action: HomeActions.LatestFetch.DownloadCompleted, dep: Dependencies): List<Action> {
    try {
        val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, action.payload)
        dep.storage.settings.putString("LATEST", json)
        dep.storage.settings.putLong("DOWNLOAD_TIMESTAMP_KEY", currentTimeMillis())
    } catch (e: Exception) {
        dep.utils.log.v { e.toString() }
    } finally {
        return listOf(HomeActions.LatestFetch.LoadFromCache)
    }
}

suspend fun handleLoadLatestFromCache(action: Action, dep: Dependencies): List<Action> {
    try {
        val apodJson = dep.storage.settings["LATEST", ""]
        if (apodJson == "") {
            return listOf(HomeActions.LatestFetch.Error("Nothing cached"))
        } else {
            return listOf(
                HomeActions.LatestFetch.Completed(
                    Json(
                        JsonConfiguration.Stable
                    ).parse(Apod.serializer().list, apodJson)
                )
            )
        }
    } catch (e: Exception) {
        dep.utils.log.v { e.toString() }
        return listOf(HomeActions.LatestFetch.Error(e.toString()))
    }
}
