package com.shadowings.apodkmp.mock

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.shadowings.apodkmp.redux.Dependencies
import com.shadowings.apodkmp.redux.Http
import com.shadowings.apodkmp.redux.Platforms
import com.shadowings.apodkmp.redux.Storage
import com.shadowings.apodkmp.redux.Utils
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.http.fullPath
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getMockDeps() = Dependencies(
    utils = Utils(
        getActionName = {
            if (it::class.qualifiedName != null) {
                it::class.qualifiedName!!
            }
            it.toString()
        },
        platform = Platforms.Android,
        log = Kermit(CommonLogger()).withTag("APOD"),
        date = {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -it)
            SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)
        },
        currentTimeMillis = { 10 }
    ),
    http = Http(
        httpClient = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    error("Unhandled ${request.url.fullPath}")
                }
            }
        }
    ),
    storage = Storage(
        settings = VolatileSettings()
    )
)
