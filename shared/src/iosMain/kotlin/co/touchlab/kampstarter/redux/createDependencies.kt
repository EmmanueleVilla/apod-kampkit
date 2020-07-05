package co.touchlab.kampstarter.redux

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSUserDefaults

actual fun createDependencies(): Dependencies {
    val log = Kermit(NSLogLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                if(it::class.qualifiedName != null) {
                    it::class.qualifiedName!!
                }
                it.toString()
            },
            getPlatform = { Platforms.IOS },
            log = log,
            today = {
                val formatter = NSDateFormatter()
                formatter.dateFormat = "yyyy-MM-dd"
                formatter.stringFromDate(NSDate())
            }
        ),
        http = Http(),
        storage = Storage(
            settings = AppleSettings(NSUserDefaults(suiteName = "APOD_SETTINGS"))
        )
    )
}
