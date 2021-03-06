package com.shadowings.apodkmp.redux

import android.content.Context
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.russhwolf.settings.AndroidSettings
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.koin.core.KoinComponent
import org.koin.core.get

actual fun createDependencies(): Dependencies {
    val container = object : KoinComponent {
        val appContext: Context = get()
    }
    val log = Kermit(LogcatLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                if (it::class.qualifiedName != null) {
                    it::class.qualifiedName!!
                }
                it.toString()
            },
            platform = Platforms.Android,
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
            settings = AndroidSettings(container.appContext.getSharedPreferences("APOD_SETTINGS", Context.MODE_PRIVATE))
        ),
        constants = Constants()
    )
}
