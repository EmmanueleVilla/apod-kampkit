package com.shadowings.apodkmp.redux

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.JsSettings
import kotlin.js.Date
import kotlin.math.roundToLong

actual fun createDependencies(): Dependencies {
    val log = Kermit(CommonLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                it.toString()
            },
            platform = Platforms.Js,
            log = log,
            date = {
                // no need to use the parameter, we only need it in the server logic
                val today = Date()
                val day: Int = today.getDate()
                val month: Int = today.getMonth() + 1
                val year: Int = today.getFullYear()
                val dd: String = if (day < 10) "0$day" else day.toString()
                val mm: String = if (month < 10) "0$month" else month.toString()
                "$year-$mm-$dd"
            },
            currentTimeMillis = { Date().getTime().roundToLong() }
        ),
        http = Http(),
        storage = Storage(
            settings = JsSettings()
        ),
        constants = Constants()
    )
}
