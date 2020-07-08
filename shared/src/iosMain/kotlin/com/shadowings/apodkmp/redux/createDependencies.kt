package com.shadowings.apodkmp.redux

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSUserDefaults
import platform.Foundation.timeIntervalSince1970

actual fun createDependencies(): Dependencies {
    val log = Kermit(NSLogLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                if (it::class.qualifiedName != null) {
                    it::class.qualifiedName!!
                }
                it.toString()
            },
            platform = Platforms.IOS,
            log = log,
            date = {
                // no need to use the parameter, we only need it in the server logic
                val formatter = NSDateFormatter()
                formatter.dateFormat = "yyyy-MM-dd"
                formatter.stringFromDate(NSDate())
            },
            currentTimeMillis = { (NSDate().timeIntervalSince1970 * 1000).toLong() }
        ),
        http = Http(),
        storage = Storage(
            settings = AppleSettings(NSUserDefaults(suiteName = "APOD_SETTINGS"))
        )
    )
}
