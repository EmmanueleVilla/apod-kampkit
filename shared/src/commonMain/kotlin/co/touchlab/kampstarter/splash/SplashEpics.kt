package co.touchlab.kampstarter.splash

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.redux.AppState
import co.touchlab.kampstarter.redux.Epic
import co.touchlab.kampstarter.redux.SL
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.Settings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal val splashEpics: Epic <AppState> = { store, state, action ->
    when (action) {
        is SplashActions.ApodFetch.Request -> {
            handleApodRequest(store, state, action)
        }

        is SplashActions.ApodFetch.FetchFromWeb -> {
            handleFetchFromWeb(store, state, action)
        }
    }
}

private val handleApodRequest: Epic <AppState> = { store, state, action ->
    val settings = SL[Settings::class] as Settings
    val lastDownloadTimeMS = settings.getLong("DB_TIMESTAMP_KEY", 0)
    val oneHourMS = 60 * 60 * 1000
    val expired = (lastDownloadTimeMS + oneHourMS < currentTimeMillis())
    if(expired) {
        store.dispatch(SplashActions.ApodFetch.FetchFromWeb)
    } else {
        store.dispatch(SplashActions.ApodFetch.LoadFromCache)
    }
}

private val handleFetchFromWeb: Epic <AppState> = { store, state, action ->
    GlobalScope.launch {
        try {
            val ktorApi: KtorApi = SL[KtorApi::class] as KtorApi
            val log : Kermit = SL[Kermit::class] as Kermit
            val dbHelper: DatabaseHelper = SL[DatabaseHelper::class] as DatabaseHelper
            val settings: Settings = SL[Settings::class] as Settings

            val apodResult = ktorApi.get()
            log.v { "Fetched ${apodResult.date} apod from network" }
            dbHelper.insertApods(listOf(apodResult))
            settings.putLong("DB_TIMESTAMP_KEY", currentTimeMillis())
        } catch (e: Exception) {
            store.dispatch(SplashActions.ApodFetch.LoadFromCache)
        }
    }
}
