package com.shadowings.apodkmp.redux

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.ExperimentalJvm
import com.russhwolf.settings.JvmPropertiesSettings
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Properties

@ExperimentalJvm
actual fun createDependencies(): Dependencies {
    val log = Kermit(CommonLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                if (it::class.qualifiedName != null) {
                    it::class.qualifiedName!!
                }
                it.toString()
            },
            getPlatform = { Platforms.Jvm },
            log = log,
            today = { SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().time) }
        ),
        http = Http(),
        storage = Storage(
            settings = JvmPropertiesSettings(Properties())
        )
    )
}
