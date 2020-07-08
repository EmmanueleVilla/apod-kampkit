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
            platform = Platforms.Jvm,
            log = log,
            date = {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -it)
                SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)
            },
            currentTimeMillis = { System.currentTimeMillis() }
        ),
        http = Http(),
        storage = Storage(
            settings = JvmPropertiesSettings(Properties())
        )
    )
}
