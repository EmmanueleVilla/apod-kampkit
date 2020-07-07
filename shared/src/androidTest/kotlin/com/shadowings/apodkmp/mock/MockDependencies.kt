package com.shadowings.apodkmp.mock

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.shadowings.apodkmp.redux.Dependencies
import com.shadowings.apodkmp.redux.Http
import com.shadowings.apodkmp.redux.Platforms
import com.shadowings.apodkmp.redux.Storage
import com.shadowings.apodkmp.redux.Utils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getMockDeps() = mockDep.copy()

private val mockDep = Dependencies(
    utils = Utils(
        getActionName = {
            if (it::class.qualifiedName != null) {
                it::class.qualifiedName!!
            }
            it.toString()
        },
        getPlatform = { Platforms.Android },
        log = Kermit(CommonLogger()).withTag("APOD"),
        date = {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -it)
            SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)
        }
    ),
    http = Http(),
    storage = Storage(
        settings = VolatileSettings()
    )
)
