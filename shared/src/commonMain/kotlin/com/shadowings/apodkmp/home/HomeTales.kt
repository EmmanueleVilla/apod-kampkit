package com.shadowings.apodkmp.home
import com.russhwolf.settings.get
import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.Action
import com.shadowings.apodkmp.redux.Dependencies
import com.shadowings.apodkmp.redux.Platforms
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

suspend fun homeTales(action: Action, dep: Dependencies): List<Action> {
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

fun handleLatestRequest(action: Action, dep: Dependencies): List<Action> {
    return try {
        val lastDownloadTimeMS = dep.storage.settings.getLong(dep.constants.latestTimestampKey, 0)
        val oneHourMS = 60 * 60 * 1000
        val expired = (lastDownloadTimeMS + oneHourMS < dep.utils.currentTimeMillis())
        if (expired || dep.utils.platform == Platforms.Js) {
            listOf(HomeActions.LatestFetch.FetchFromWeb)
        } else {
            listOf(HomeActions.LatestFetch.LoadFromCache)
        }
    } catch (e: Exception) {
        listOf(HomeActions.LatestFetch.FetchFromWeb)
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
        return if (dep.utils.platform != Platforms.Js) {
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

fun handleDownloadCompleted(action: HomeActions.LatestFetch.DownloadCompleted, dep: Dependencies): List<Action> {
    return try {
        val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, action.payload)
        dep.storage.settings.putString(dep.constants.latestKey, json)
        dep.storage.settings.putLong(dep.constants.latestTimestampKey, dep.utils.currentTimeMillis())
        listOf(HomeActions.LatestFetch.LoadFromCache)
    } catch (e: Exception) {
        dep.utils.log.v { e.toString() }
        listOf(HomeActions.LatestFetch.Error(e.toString()))
    }
}

fun handleLoadLatestFromCache(action: Action, dep: Dependencies): List<Action> {
    try {
        val apodJson = dep.storage.settings[dep.constants.latestKey, ""]
        return if (apodJson == "") {
            listOf(HomeActions.LatestFetch.Error("Nothing cached"))
        } else {
            listOf(
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
