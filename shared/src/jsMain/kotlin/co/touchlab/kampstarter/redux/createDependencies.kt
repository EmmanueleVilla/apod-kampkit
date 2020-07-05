package co.touchlab.kampstarter.redux

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.JsSettings
import kotlin.js.Date

actual fun createDependencies(): Dependencies {
    val log = Kermit(CommonLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                it.toString()
            },
            getPlatform = { Platforms.Js },
            log = log,
            today = {
                val today = Date()
                val day : Int = today.getDate()
                var month : Int = today.getMonth() + 1
                val year : Int = today.getFullYear()
                val dd : String = if(day < 10) "0$day" else day.toString()
                val MM : String = if(month < 10) "0$month" else month.toString()
                "$year-$MM-$dd"
            }
        ),
        http = Http(),
        storage = Storage(
            settings = JsSettings()
        )
    )
}
